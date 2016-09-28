package com.datalife.services;

import com.datalife.entities.*;
import com.datalife.repositories.*;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.Pipeline;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.itextpdf.tool.xml.pipeline.html.LinkProvider;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Created by DATASCRIBE on 3/30/2015.
 */
@Service
public class PDFGenerationService {
    @Autowired
    CommonRepository commonRepository;

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    DoctorInfoRepository doctorInfoRepository;
    @Autowired
    UtilityServices utilityServices;
    @Autowired
    UserContactRepository userContactRepository;
    @Autowired
    UserDetailsRepository userDetailsRepository;

    private static final Logger logger = Logger.getLogger(PDFGenerationService.class);

    StringBuilder sb = new StringBuilder();

    public String convertObjectsToString(String htmlString, Encounter encounter, User doctor, User clinic) {

        User user = encounter.getUser();
        Long encounterId = encounter.getEncounterId();


        //Patient user details
        UserDetails userDetails = userDetailsRepository.findByUserId(user.getUserId());
        htmlString = htmlString.replace("patientName", userDetails.getFirstName() + " " + userDetails.getLastName());
        htmlString = htmlString.replace("datevalue", DateService.dateToStringDMY(encounter.getDate()));
        if (userDetails.getGender() != null) {
            htmlString = htmlString.replace("gendervalue", userDetails.getGender());
        }else{
            htmlString = htmlString.replace("gendervalue", " - ");
        }
        String age = encounter.getAge();
        if (StringUtils.isNotBlank(age)) {
            htmlString = htmlString.replace("agevalue", age);

        } else {
            htmlString = htmlString.replace("agevalue", " - ");

        }

        //Patient Insurance details
        if (user.getInsuranceDetails() != null) {
            String policyNumber = user.getInsuranceDetails().getPolicyNumber();
            if (StringUtils.isNotBlank(policyNumber)) {
                htmlString = htmlString.replace("insNoValue", policyNumber);
            } else {
                htmlString = htmlString.replace("insNoValue", " - ");
            }
        }

        //Mini encounter
        htmlString = insertMiniEncounter(htmlString, encounter.getMiniEncounter());

        //SOAP
        htmlString = insertSOAP(htmlString, encounter.getSoap());

        //History
        htmlString = insertHistoy(htmlString, encounter.getHistory());

        //vitals
        htmlString = insertVitals(htmlString, encounter.getVitals());

        //prescription
        htmlString = inserPrescriptions(htmlString, encounter.getPrescriptions());

        //Review of Systems
        htmlString = insertReviewOfSystem(htmlString, encounter.getReviewofSystems());

        //Physical Examinations
        htmlString = insertPhysicalExamination(htmlString, encounterId, encounter.getPhysicalExaminations());

        //Lab Orders
        htmlString = insertLaborders(htmlString, encounter.getLabOrders());

        //Doctor Details
        if (doctor != null) {
            String doctorName  = doctor.getUserDetails().getFirstName() + " " + doctor.getUserDetails().getLastName();
            htmlString = htmlString.replace("doctorName", doctorName);
            if (clinic != null) {
                htmlString = htmlString.replace("cName", clinic.getClinicInfo().getClinicName());
            } else {
                htmlString = htmlString.replace("cName", "*****");
            }
            String mrnNumber = doctor.getDoctorInfo().getMlrNumber();
            if (StringUtils.isNotBlank(mrnNumber)) {
                htmlString = htmlString.replace("MRNumber", mrnNumber);
            } else {
                htmlString = htmlString.replace("MRNumber", "NONE");
            }

        } else {
            htmlString = htmlString.replace("doctorName", encounter.getDoctorName());
            htmlString = htmlString.replace("cName", encounter.getClinicName());
            htmlString = htmlString.replace("MRNumber", encounter.getMciNumber());
        }

        //Clinic Details
        if (clinic != null && clinic.getUserContactInfo() != null) {
            UserContactInfo uc = clinic.getUserContactInfo();
            String address = uc.getAddress() + "," + uc.getLocation();
            String address1 = "," + uc.getCity() + "-" + uc.getZipCode();
            if (StringUtils.isNotBlank(address) && StringUtils.isNotBlank(address1)) {
                htmlString = htmlString.replace("adress", address);
                htmlString = htmlString.replace("new", address1);
            } else {
                htmlString = htmlString.replace("adress", "*****");
                htmlString = htmlString.replace("new", " ");
            }
        } else {
            htmlString = htmlString.replace("adress", "*****");
            htmlString = htmlString.replace("new", " ");
        }
        return htmlString;
    }


    public LinkedHashMap<String, Map<String, String>> getPhysicalExamination(Long encounterId, List<PhysicalExamination> peList) {
        LinkedHashMap<String, String> stringMap;
        LinkedHashMap<String, Map<String, String>> resultMap = new LinkedHashMap<>();

        if (encounterId != null) {
            peList = commonRepository.getPeName(encounterId);
        }
        for (PhysicalExamination examination : peList) {
            String name = commonRepository.getPENameById(examination.getPeTypeId());
            String causeDes = examination.getDescription();
            switch (name) {

                case "Cardiovascular":
                    if (examination.getCardioVascular() != null && examination.getCardioVascular().size() != 0) {
                        stringMap = new LinkedHashMap<>();
                        stringMap.put(name + ":", "");
                        for (int j = 0; j < examination.getCardioVascular().size(); j++) {
                            CardioVascular vascular = examination.getCardioVascular().get(j);
                            if ("checkbox".equals(vascular.getCardiovascularLabels().getLabelType())) {
                                String str = stringMap.get("checkbox");
                                if (str == null) {
                                    stringMap.put("checkbox", commonRepository.getcardioLableNameById(vascular.getCardiovascularLabels().getCardioLabelId()));
                                } else {
                                    stringMap.put("checkbox", str + ", " + commonRepository.getcardioLableNameById(vascular.getCardiovascularLabels().getCardioLabelId()));
                                }
                            }
                            if ("text".equals(vascular.getCardiovascularLabels().getLabelType())) {
                                String str = stringMap.get("text");
                                if (str == null) {
                                    stringMap.put("text", commonRepository.getcardioLableNameById(vascular.getCardiovascularLabels().getCardioLabelId()) + " : " + vascular.getDescriptions());
                                } else {
                                    stringMap.put("text", str + ", " + commonRepository.getcardioLableNameById(vascular.getCardiovascularLabels().getCardioLabelId()) + " : " + vascular.getDescriptions());
                                }
                            }
                            if ("textarea".equals(vascular.getCardiovascularLabels().getLabelType())) {
                                stringMap.put("textarea", vascular.getDescriptions());
                            }
                        }
                        resultMap.put(name, stringMap);
                    } else {
                        stringMap = new LinkedHashMap<>();
                        if (StringUtils.isNotBlank(causeDes)) {
                            stringMap.put(name, causeDes);
                            resultMap.put(name, stringMap);
                        }
                    }
                    break;
                case "Gastrointestinal":
                    if (examination.getGastrointestinal() != null && examination.getGastrointestinal().size() != 0) {
                        stringMap = new LinkedHashMap<>();
                        stringMap.put(name + ":", "");
                        for (int j = 0; j < examination.getGastrointestinal().size(); j++) {
                            Gastrointestinal gastrointestinal = examination.getGastrointestinal().get(j);
                            if ("checkbox".equals(gastrointestinal.getGastrointestinalLabels().getLabelType())) {
                                String str = stringMap.get("checkbox");
                                if (str == null) {
                                    stringMap.put("checkbox", commonRepository.getgastroLableNameById(gastrointestinal.getGastrointestinalLabels().getGastroLabelId()));
                                } else {
                                    stringMap.put("checkbox", str + ", " + commonRepository.getgastroLableNameById(gastrointestinal.getGastrointestinalLabels().getGastroLabelId()));
                                }
                            }
                            if ("text".equals(gastrointestinal.getGastrointestinalLabels().getLabelType())) {
                                String str = stringMap.get("text");
                                if (str == null) {
                                    stringMap.put("text", commonRepository.getgastroLableNameById(gastrointestinal.getGastrointestinalLabels().getGastroLabelId()) + " : " + gastrointestinal.getDescriptions());
                                } else {
                                    stringMap.put("text", str + ", " + commonRepository.getgastroLableNameById(gastrointestinal.getGastrointestinalLabels().getGastroLabelId()) + " : " + gastrointestinal.getDescriptions());
                                }
                            }
                            if ("textarea".equals(gastrointestinal.getGastrointestinalLabels().getLabelType())) {
                                stringMap.put("textarea", gastrointestinal.getDescriptions());
                            }
                        }
                        resultMap.put(name, stringMap);

                    } else {
                        stringMap = new LinkedHashMap<>();
                        if (StringUtils.isNotBlank(causeDes)) {
                            stringMap.put(name, causeDes);
                            resultMap.put(name, stringMap);
                        }
                    }
                    break;
                case "Muscoloskeletal":
                    if (examination.getMuscoloskeletal() != null && examination.getMuscoloskeletal().size() != 0) {
                        stringMap = new LinkedHashMap<>();
                        stringMap.put(name + ":", "");
                        for (int j = 0; j < examination.getMuscoloskeletal().size(); j++) {
                            Muscoloskeletal muscoloskeletal = examination.getMuscoloskeletal().get(j);
                            if ("checkbox".equals(muscoloskeletal.getMuscoloskeletalLabels().getLabelType())) {
                                String str = stringMap.get("checkbox");
                                if (str == null) {
                                    stringMap.put("checkbox", commonRepository.getmuscoloLableNameById(muscoloskeletal.getMuscoloskeletalLabels().getMuscoloLabelId()));
                                } else {
                                    stringMap.put("checkbox", str + ", " + commonRepository.getmuscoloLableNameById(muscoloskeletal.getMuscoloskeletalLabels().getMuscoloLabelId()));
                                }
                            }
                            if ("text".equals(muscoloskeletal.getMuscoloskeletalLabels().getLabelType())) {
                                String str = stringMap.get("text");
                                if (str == null) {
                                    stringMap.put("text", commonRepository.getmuscoloLableNameById(muscoloskeletal.getMuscoloskeletalLabels().getMuscoloLabelId()) + " : " + muscoloskeletal.getDescriptions());
                                } else {
                                    stringMap.put("text", str + ", " + commonRepository.getmuscoloLableNameById(muscoloskeletal.getMuscoloskeletalLabels().getMuscoloLabelId()) + " : " + muscoloskeletal.getDescriptions());
                                }
                            }
                            if ("textarea".equals(muscoloskeletal.getMuscoloskeletalLabels().getLabelType())) {
                                stringMap.put("textarea", muscoloskeletal.getDescriptions());
                            }
                        }
                        resultMap.put(name, stringMap);

                    } else {
                        stringMap = new LinkedHashMap<>();
                        if (StringUtils.isNotBlank(causeDes)) {
                            stringMap.put(name, causeDes);
                            resultMap.put(name, stringMap);
                        }
                    }
                    break;
                case "Respiratory":
                    if (examination.getRespiratory() != null && examination.getRespiratory().size() != 0) {
                        stringMap = new LinkedHashMap<>();
                        stringMap.put(name + ":", "");
                        for (int j = 0; j < examination.getRespiratory().size(); j++) {
                            Respiratory respiratory = examination.getRespiratory().get(j);
                            if ("checkbox".equals(respiratory.getRespiratoryLabels().getLabelType())) {
                                String str = stringMap.get("checkbox");
                                if (str == null) {
                                    stringMap.put("checkbox", commonRepository.getrespLableNameById(respiratory.getRespiratoryLabels().getRespiratoryLabelId()));
                                } else {
                                    stringMap.put("checkbox", str + ", " + commonRepository.getrespLableNameById(respiratory.getRespiratoryLabels().getRespiratoryLabelId()));
                                }
                            }

                            if ("textarea".equals(respiratory.getRespiratoryLabels().getLabelType())) {
                                stringMap.put("textarea", respiratory.getDescriptions());
                            }
                        }
                        resultMap.put(name, stringMap);

                    } else {
                        stringMap = new LinkedHashMap<>();
                        if (StringUtils.isNotBlank(causeDes)) {
                            stringMap.put(name, causeDes);
                            resultMap.put(name, stringMap);
                        }
                    }
                    break;
                default:
                    stringMap = new LinkedHashMap<>();
                    stringMap.put(name, causeDes);
                    resultMap.put(name, stringMap);

            }

        }
        return resultMap;
    }


    public LinkedHashMap<String, String> getLabList(List<LabOrder> labOrders) {
        LinkedHashMap<String, String> stringMap = new LinkedHashMap<>();
        for (LabOrder labOrder : labOrders) {
            LabTests order = new LabTests();
            order.setLabTestsId(labOrder.getLabTestsId());
            if (order.getLabTestsId() != null) {
                LabTests labTestses = commonRepository.getLabTestsName(order.getLabTestsId());

                String name = labTestses.getLabCategories().getName();
                String testName = labTestses.getName();
                String otherDes = labOrder.getOtherDesp();
                String str;
                if (!stringMap.containsKey(name)) {
                    if ("Others".equals(name)) {
                        stringMap.put(name, otherDes);
                    } else {
                        stringMap.put(name, testName);
                    }
                } else {
                    if (!"Others".equals(testName)) {
                        str = stringMap.get(name) + "," + testName;
                    } else {
                        str = stringMap.get(name) + " " + ", " + otherDes;
                    }
                    stringMap.put(name, str);
                }

            }
        }
        return stringMap;
    }


    public File qrCode(File qrFile) {
        String myCodeText = "PATIENT VISIT SUMMARY\n" +
                "Patient Name : patientName\n" +
                "Date : datevalue\n" +
                "CHIEF COMPLAINT\n" +
                " \n" +
                " \n" +
                " \n" +
                "ALLERGIES\n" +
                "-Food\n" +
                "-Dust\n" +
                "SOCIAL HISTORY\n" +
                "the primary symptom that a patient states as the reason for seeking medical care. the primary symptom that a patient states as the reason for seeking medical care. the primary symptom that a patient states as the reason for seeking medical care. the primary symptom that a patient states as the reason for seeking medical care.\n" +
                "FAMILY HISTORY\n" +
                "the primary symptom that a patient states as the reason for seeking medical care. the primary symptom that a patient states as the reason for seeking medical care. the primary symptom that a patient states as the reason for seeking medical care. the primary symptom that a patient states as the reason for seeking medical care.\n" +
                "REVIEW OF SYSTEMS\n" +
                "PHYSICAL EXAMINATION\n" +
                "LABORATORY AND DIAGNOSTIC STUDIES\n" +
                "ASSESSMENT\n" +
                "the primary symptom that a patient states as the reason for seeking medical care. the primary symptom that a patient states as the reason for seeking medical care. the primary symptom that a patient states as the reason for seeking medical care. the primary symptom that a patient states as the reason for seeking medical care.\n" +
                "FOLLOW-UP/REFERAL\n" +
                "the primary symptom that a patient states as the reason for seeking medical care. the primary symptom that a patient states as the reason for seeking medical care. the primary symptom that a patient states as the reason for seeking medical care. the primary symptom that a patient states as the reason for seeking medical care.";

        String fileType = "jpg";

        int size = 125;
        try {
            Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix byteMatrix = qrCodeWriter.encode(myCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
            int CrunchifyWidth = byteMatrix.getWidth();
            BufferedImage image = new BufferedImage(CrunchifyWidth, CrunchifyWidth,
                    BufferedImage.TYPE_INT_RGB);
            image.createGraphics();

            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
            graphics.setColor(Color.BLACK);

            for (int i = 0; i < CrunchifyWidth; i++) {
                for (int j = 0; j < CrunchifyWidth; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
            ImageIO.write(image, fileType, qrFile);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        return qrFile;
    }


    public boolean pdfGenerator(File file, InputStream stream, Encounter encounter,User doctor, User clinic) {

        logger.debug("method pdfGenerator : Start");

        try {
            Document document = new Document();
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(file));
            InputStream is = null;
            document.open();
            document.addAuthor("Dtalife Team");
            document.addCreator("DataLife Team");
            document.addSubject("DataLife");
            document.addCreationDate();
            document.addTitle("Patient Visit Summary");

            String htmlString = getFileContent(stream, "UTF-8");
            String output = convertObjectsToString(htmlString, encounter, doctor, clinic);
            InputStream stringStream = new ByteArrayInputStream(output.getBytes());
            HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
            htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
            htmlContext.setImageProvider(new AbstractImageProvider() {
                public String getImageRootPath() {
                    return "D:\\images";/*this.getClass().getClassLoader().getResource("").getPath();*/
                }
            });

            htmlContext.setLinkProvider(new LinkProvider() {
                public String getLinkRoot() {
                    return "http://demo.itextsupport.com/xmlworker/itextdoc/Download/";
                }
            });

            CSSResolver cssResolver = new StyleAttrCSSResolver();
             is = this.getClass().getClassLoader().getResourceAsStream("report.css");
            String css = IOUtils.toString(is);
            CssFile cssFile = XMLWorkerHelper.getCSS(new ByteArrayInputStream(css.getBytes()));
            cssResolver.addCss(cssFile);

            Pipeline<?> pipeline = new CssResolverPipeline(cssResolver, new HtmlPipeline(htmlContext, new PdfWriterPipeline(document, pdfWriter)));
            XMLWorker worker = new XMLWorker(pipeline, true);
            XMLParser p = new XMLParser(worker);
            p.parse(stringStream);
            document.close();
            logger.debug("method pdfGenerator : Done");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public String getFileContent(InputStream fis, String encoding) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(fis, encoding))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            return sb.toString();
        }
    }
/*
    public static void convertImageToPdf() {
        Document document = new Document();
        String input = "C:\\Users\\Public\\Pictures\\Sample Pictures\\25-04-2013-2.jpg"; // .gif and .jpg are ok too!
        String output = "C:\\Users\\Public\\Pictures\\Sample Pictures\\capture.pdf";
        try {
            FileOutputStream fos = new FileOutputStream(output);
            PdfWriter writer = PdfWriter.getInstance(document, fos);
            writer.open();
            document.open();
            Image img = Image.getInstance(input);
            img.scalePercent(50);
            document.add(img);
            document.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    private String insertSOAP(String htmlString, SOAP soap) {
        if (soap != null) {
            String subjective = soap.getSubjective();
            String subjectiveDiv = "<div class='srsubhead'>SUBJECTIVE</div><div class='srcontent'><p>";
            if (StringUtils.isNotBlank(subjective)) {
                htmlString = htmlString.replace("subjective", subjectiveDiv + subjective + "</p></div>");
            } else {
                htmlString = htmlString.replace("subjective", "");
            }
            String objective = soap.getObjective();
            String objectiveDiv = "<div class='srsubhead'>OBJECTIVE</div><div class='srcontent'><p>";
            if (StringUtils.isNotBlank(objective)) {
                htmlString = htmlString.replace("objective", objectiveDiv + objective + "</p></div>");
            } else {
                htmlString = htmlString.replace("objective", "");
            }
            String assessment = soap.getAssessment();
            String assessmentDiv = "<div class='srsubhead'>ASSESSMENT</div><div class='srcontent'><p>";
            if (StringUtils.isNotBlank(assessment)) {
                htmlString = htmlString.replace("assessment", assessmentDiv + assessment + "</p></div>");
            } else {
                htmlString = htmlString.replace("assessment", "");
            }
            String plan = soap.getPlan();
            String planDiv = "<div class='srsubhead'>PLAN</div><div class='srcontent'><p>";
            if (StringUtils.isNotBlank(plan)) {
                htmlString = htmlString.replace("plan", planDiv + plan + "</p></div>");
            } else {
                htmlString = htmlString.replace("plan", "");
            }
        } else {
            htmlString = htmlString.replace("plan", "");
            htmlString = htmlString.replace("assessment", "");
            htmlString = htmlString.replace("subjective", "");
            htmlString = htmlString.replace("objective", "");
        }

        return htmlString;
    }

    private String insertHistoy(String htmlString, History history) {
        if (history != null) {

            Map<String, String> medical = historyRepository.getHistoryData(history.getMedicalHistory());
            String pastMedicalHistoryDiv = " <div class='srsubhead'>PAST MEDICAL HISTORY</div><div class='srcontent'><div>";
            if (medical != null && !medical.isEmpty()) {
                String pastmedvalue = "";
                for (Map.Entry<String, String> e : medical.entrySet()) {
                    pastmedvalue += "<div>" + " * " + e.getValue().replace("(", "").replace(")", "") + "</div>";
                }
                htmlString = htmlString.replace("pastmedvalue", pastMedicalHistoryDiv + pastmedvalue + "</div></div>");
            } else {
                htmlString = htmlString.replace("pastmedvalue", "");
            }

            Map<String, String> surgical = historyRepository.getHistoryData(history.getSurgicalHistory());
            String surgicalHistoryDiv = " <div class='srsubhead'>PAST SURGICAL HISTORY</div><div class='srcontent'><div>";
            if (surgical != null && !surgical.isEmpty()) {
                String pastsurvalue = "";
                for (Map.Entry<String, String> e : surgical.entrySet()) {
                    pastsurvalue += "<div>" + " * " + e.getValue().replace("(", "").replace(")", "") + "</div>";
                }
                htmlString = htmlString.replace("pshvalue", surgicalHistoryDiv + pastsurvalue + "</div></div>");
            } else {
                htmlString = htmlString.replace("pshvalue", "");
            }

            Map<String, String> allergies = historyRepository.getHistoryData(history.getAllergies());
            String allergiesDiv = " <div class='srsubhead'>ALLERGIES</div><div class='srcontent'><div>";
            if (allergies != null && !allergies.isEmpty()) {
                String pastallvalue = "";
                for (Map.Entry<String, String> e : allergies.entrySet()) {
                    pastallvalue += "<div>" + " * " + e.getValue().replace("(", "").replace(")", "") + "</div>";
                }
                htmlString = htmlString.replace("allergiesDes", allergiesDiv + pastallvalue + "</div></div>");
            } else {
                htmlString = htmlString.replace("allergiesDes", "");
            }
            Map<String, String> familyHistory = historyRepository.getHistoryData(history.getFamilyHistory());
            String familyHistoryDiv = " <div class='srsubhead'>FAMILY HISTORY</div><div class='srcontent'><div>";
            if (familyHistory != null && !familyHistory.isEmpty()) {
                String pastfhvalue = "";
                for (Map.Entry<String, String> e : familyHistory.entrySet()) {
                    pastfhvalue += "<div>" + " * " + e.getValue().replace("(", "").replace(")", "") + "</div>";
                }
                htmlString = htmlString.replace("familyhisvalue", familyHistoryDiv + pastfhvalue + "</div></div>");
            } else {
                htmlString = htmlString.replace("familyhisvalue", "");
            }

            Map<String, String> socialHistory = historyRepository.getHistoryData(history.getSocialHistory());
            String socialHistoryDiv = " <div class='srsubhead'>SOCIAL HISTORY</div><div class='srcontent'><div>";
            if (socialHistory != null && !socialHistory.isEmpty()) {
                String pastschvalue = "";
                for (Map.Entry<String, String> e : socialHistory.entrySet()) {
                    pastschvalue += "<div>" + " * " + e.getValue().replace("(", "").replace(")", "") + "</div>";
                }
                htmlString = htmlString.replace("socialhisvalue", socialHistoryDiv + pastschvalue + "</div></div>");
            } else {
                htmlString = htmlString.replace("socialhisvalue", "");
            }

        } else {
            htmlString = htmlString.replace("socialhisvalue", "");
            htmlString = htmlString.replace("familyhisvalue", "");
            htmlString = htmlString.replace("allergiesDes", "");
            htmlString = htmlString.replace("pshvalue", "");
            htmlString = htmlString.replace("pastmedvalue", "");
        }
        return htmlString;
    }


    private String insertMiniEncounter(String htmlString, MiniEncounter miniEncounter) {
        if (miniEncounter != null) {
            String chiefComplaint = miniEncounter.getChiefComplaint();
            String chiefComplaintDiv = "<div class='srsubhead'>CHIEF COMPLAINT</div><div class='srcontent'><p>";
            if (StringUtils.isNotBlank(chiefComplaint)) {
                htmlString = htmlString.replace("ccvalue", chiefComplaintDiv + chiefComplaint + "</p></div>");
            } else {
                htmlString = htmlString.replace("ccvalue", "");
            }

            //HPI

            String hpi = miniEncounter.getHpi();
            String hpiDiv = "<div class='srsubhead'>HISTORY OF PRESENT ILLNESS</div><div class='srcontent'><p>";
            if (StringUtils.isNotBlank(hpi)) {
                htmlString = htmlString.replace("hpivalue", hpiDiv + hpi + "</p></div>");
            } else {
                htmlString = htmlString.replace("hpivalue", "");
            }

            //Impression

            String imp = miniEncounter.getImpression();
            String impDiv = " <div class=\"srsubhead\">ASSESSMENT / DIAGNOSIS</div>\n" +
                    "    <div class=\"srcontent\"><p>";
            if (StringUtils.isNotBlank(imp)) {
                htmlString = htmlString.replace("impression", impDiv + imp + "</p></div>");
            } else {
                htmlString = htmlString.replace("impression",impDiv + "Not Recorded" + "</p></div>");
            }


            //Impression

            String plan = miniEncounter.getPlan();
            String planDiv = " <div class=\"srsubhead\">PLAN</div>\n" +
                    "    <div class=\"srcontent\"><p>";
            if (StringUtils.isNotBlank(plan)) {
                htmlString = htmlString.replace("plan", planDiv + plan + "</p></div>");
            } else {
                htmlString = htmlString.replace("plan",planDiv + "Not Recorded" + "</p></div>");
            }

            //Procedures

            String procedure = miniEncounter.getProcedures();
            String procedureDiv = " <div class=\"srsubhead\">PROCEDURE/S PERFORMED</div>\n" +
                    "    <div class=\"srcontent\"><p>";
            if (StringUtils.isNotBlank(procedure)) {
                htmlString = htmlString.replace("procedures", procedureDiv + procedure + "</p></div>");
            } else {
                htmlString = htmlString.replace("procedures", "");
            }

            //Follow up

            String foll = miniEncounter.getFollowup();
            String follDiv = " <div class=\"srsubhead\">FOLLOW-UP/REFERRAL</div>\n" +
                    "    <div class=\"srcontent lastcontent\"><p>";
            if (StringUtils.isNotBlank(foll)) {
                htmlString = htmlString.replace("followup", follDiv + foll + "</p></div>");
            } else {
                htmlString = htmlString.replace("followup", "");
            }
        } else {
            htmlString = htmlString.replace("ccvalue", "");
            htmlString = htmlString.replace("hpivalue", "");
            htmlString = htmlString.replace("impression", "");
            htmlString = htmlString.replace("procedures", "");
            htmlString = htmlString.replace("followup", "");
        }

        return htmlString;
    }

    private String insertVitals(String htmlString, Vitals vitals) {
        if (vitals != null) {

            String temp = vitals.getTemp();
            String bp = vitals.getBp();
            String hr = vitals.getHeartRate();
            String rr = vitals.getRespRate();
            String ht = vitals.getHeight();
            String wt = vitals.getWeight();
            String bmi = vitals.getBmi();
            String sugar = vitals.getSugar();
            if (!(StringUtils.isNotBlank(temp) || StringUtils.isNotBlank(bp) || StringUtils.isNotBlank(hr) || StringUtils.isNotBlank(rr) ||
                    StringUtils.isNotBlank(ht) || StringUtils.isNotBlank(wt) || StringUtils.isNotBlank(bmi) || StringUtils.isNotBlank(sugar))) {
                htmlString = htmlString.replace("vitals", "");
            } else {
                String encounterVitalsDiv = " <div class=\"srsubhead\">VITAL SIGNS</div>\n" +
                        "    <div class=\"srcontent\">\n" + "<div>";
                if (StringUtils.isNotBlank(temp)) {
                    encounterVitalsDiv += "<div> Temperature      :    " + temp +" "+ "<span>Fahrenheit</span></div>";
                }

                if (StringUtils.isNotBlank(bp)) {
                    encounterVitalsDiv += "<div> BloodPressure    :    " + bp + " "+"<span>mmHg</span></div>";

                }

                if (StringUtils.isNotBlank(hr)) {
                    encounterVitalsDiv += "<div> PulseRate        :    " + hr + " "+"<span>Beats /min</span></div>";

                }

                if (StringUtils.isNotBlank(rr)) {
                    encounterVitalsDiv += "<div>  RespiratoryRate  :    " + rr + " "+"<span>Breaths /min</span></div>";

                }
                if (StringUtils.isNotBlank(ht)) {
                    encounterVitalsDiv += "<div>  Height           :    " + ht +" "+ "</div>";

                }
                if (StringUtils.isNotBlank(wt)) {
                    encounterVitalsDiv += "<div>  Weight           :    " + wt + " "+"<span>Kg</span></div>";

                }
                if (StringUtils.isNotBlank(bmi)) {
                    encounterVitalsDiv += "<div> BMI               :    " + bmi + " "+"<span>Kg/m2</span></div>";

                }
                if (StringUtils.isNotBlank(sugar)) {
                    encounterVitalsDiv += "<div>  Sugar            :    " + sugar + " "+"<span>mg/dL</span></div>";

                }

                htmlString = htmlString.replace("vitals", encounterVitalsDiv +
                        "   </div></div>");
            }

        } else {
            htmlString = htmlString.replace("vitals", "");
        }
        return htmlString;
    }

    private String insertReviewOfSystem(String htmlString, List<ReviewofSystems> ros) {
        String reviewofSystemsesDiv = " <div class=\"srsubhead\">REVIEW OF SYSTEMS</div>\n" +
                "    <div class=\"srcontent\">";
        if (ros != null && !ros.isEmpty()) {
            LinkedHashMap<String, String> stringMap = new LinkedHashMap<>();
            for (byte i = 0; i < ros.size(); i++) {
                String name = commonRepository.getROSCategoryName(ros.get(i).getRosId());
                stringMap.put(name, ros.get(i).getDescription());
            }
            for (Map.Entry<String, String> entry : stringMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
            /*    if(key.equals("Others")){
                    reviewofSystemsesDiv += "<div><span style='width:50px;'>" + "</span><span>" + " "+value + "</span></div>";
                }else{*/
                    reviewofSystemsesDiv += "<div><span>" + key+ " "+ "</span>:<span>" + " "+value + "</span></div>";


            }
            htmlString = htmlString.replace("selectedROS", reviewofSystemsesDiv + "</div>");
        } else {
            htmlString = htmlString.replace("selectedROS", "");
        }
        return htmlString;
    }

    private String inserPrescriptions(String htmlString, List<Prescription> prescriptions) {
        if (prescriptions != null && !prescriptions.isEmpty()) {

            String prescriptionsDiv = " <div class=\"srsubhead\">MEDICATIONS</div>\n" +
                    "    <div class=\"srcontent\">\n" +
                    "        <p>\n" +
                    "        <table style=\"\">\n" +
                    "            <thead>\n" +
                    "            <tr>\n" +
                    "                <th>Medication Name</th>\n" +
                    "                <th>Strength</th>\n" +
                    "                <th>Frequency</th>\n" +
                    "                <th>No. of Days</th>\n" +
                    "                <th>Route</th>\n" +
                    "            </tr>\n" +
                    "            </thead>\n" +
                    "            <tbody>";

            for (Prescription prescription : prescriptions) {
                String temp =
                        "            <tr>\n" +
                                "                <td class=\"mn\">"+ prescription.getMedicationName()+"</td>\n" +
                                "                <td class=\"str\">"+prescription.getStrength()+"</td>\n" +
                                "                <td class=\"freq\">"+prescription.getFrequency()+"</td>\n" +
                                "                <td class=\"qty\">"+ prescription.getQuantity().toString()+"</td>\n" +
                                "                <td class=\"fill\">"+prescription.getRoute()+"</td>\n" +
                                "            </tr>\n";
              prescriptionsDiv= prescriptionsDiv+temp;
            }
            htmlString = htmlString.replace("prescriptions", prescriptionsDiv + sb + "</tbody>\n" +
                    "        </table>\n" +
                    "        </p>\n" +
                    "    </div>");
            sb.setLength(0);
        } else {
            htmlString = htmlString.replace("prescriptions", "");

        }

        return htmlString;

    }

    private String insertPhysicalExamination(String htmlString, Long encounterId, List<PhysicalExamination> physicalExaminations) {
        String physicalExaminationsDiv = " <div class=\"srsubhead\">PHYSICAL EXAMINATION</div>\n" +
                "    <div class=\"srcontent\"><div>";

        if (physicalExaminations != null && !physicalExaminations.isEmpty()) {

            LinkedHashMap<String, Map<String, String>> resultMap = getPhysicalExamination(encounterId, physicalExaminations);
            for (Map.Entry<String, Map<String, String>> mapEntry : resultMap.entrySet()) {
                String mainKey = mapEntry.getKey();

                for (Map.Entry<String, String> entry : mapEntry.getValue().entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();

                    if (mainKey.equals(key)) {
                        key = key.replace(key, key + " : " + value);
                    } else {

                        if (value != null && !value.isEmpty()) {
                            if ("text".equals(key)) {
                                key = key.replace(key, value);
                            } else if ("checkbox".equals(key)) {
                                key = key.replace(key, "Positive for " + value);
                            } else if ("textarea".equals(key)) {
                                key = key.replace(key, "Differential Diagnoses : " + value);
                            }
                        }
                    }
                    sb.append("<p>" + key + "</p>");
                }
            }

            htmlString = htmlString.replace("physicalExamination", physicalExaminationsDiv + sb + "</div></div>");
            sb.setLength(0);
        } else {
            htmlString = htmlString.replace("physicalExamination", physicalExaminationsDiv + "Not recorded" + "</div></div>");
        }

        return htmlString;
    }

    private String insertLaborders(String htmlString, List<LabOrder> labOrders) {
        if (labOrders != null && !labOrders.isEmpty()) {
            String labOrdersDiv = "  <div class=\"srsubhead\">LABORATORY AND DIAGNOSTIC STUDIES</div>\n" +
                    "    <div class=\"srcontent\">\n" +
                    "        <div>";

            LinkedHashMap<String, String> stringMap = getLabList(labOrders);
            for (Map.Entry<String, String> entry : stringMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (value != null && !value.isEmpty()) {
                    if ("Others".equals(key)) {
                        key = key.replace(key, key + ": " + value);
                    } else {
                        key = key.replace(key, key + ": Test for " + value);
                    }
                }
                sb.append("<p>" + key + "</p>");
            }
            htmlString = htmlString.replace("labOrder", labOrdersDiv + sb + "</div></div>");

            sb.setLength(0);
        } else {
            htmlString = htmlString.replace("labOrder", "");
        }


        return htmlString;
    }
}
