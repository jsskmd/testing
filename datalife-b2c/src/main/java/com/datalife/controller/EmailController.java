package com.datalife.controller;

import com.datalife.entities.ClinicInfo;
import com.datalife.entities.User;
import com.datalife.entities.UserDetails;
import com.datalife.repositories.EncounterRepository;
import com.datalife.services.AlfrescoAuthDetails;
import com.datalife.services.UtilityServices;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Controller class that handles the requests for the application Email Services.
 * <p/>
 * Created by supriya gondi on 10/27/2014.
 */
@Service
public class EmailController implements Runnable{

    protected static Logger LOGGER = Logger.getLogger("EmailController");
    /**
     * Reference to mailSender
     */
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    EncounterRepository encounterRepository;

    @Autowired
    AlfrescoAuthDetails alfrescoAuthDetails;

    /**
     * reference to uploadDirectory
     */
    @Value("C:\\Users\\DATASCRIBE\\IdeaProjects\\cando\\src\\main\\webapp\\resources\\profileImages\\")
    private String uploadDirectory;


    /**
     * Reference to preConfiguredMessage
     */
    @Autowired
    private SimpleMailMessage preConfiguredMessage;
    /**
     * Reference to velocityEngine
     */
    @Autowired
    private VelocityEngine velocityEngine;

    @Autowired
    UtilityServices utilityServices;

    @Autowired
    ServletContext servletContext;

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public JavaMailSender getMailSender() {
        return mailSender;
    }

    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public SimpleMailMessage getPreConfiguredMessage() {
        return preConfiguredMessage;
    }

    public void setPreConfiguredMessage(SimpleMailMessage preConfiguredMessage) {
        this.preConfiguredMessage = preConfiguredMessage;
    }

    /**
     * This method is for sending Activation Link or Activation code to registered mail after registration is done
     *
     * @param user
     * @param result
     * @param modelMap
     * @return
     */
    /*@Async*/
    public boolean sendActivationLink(final User user, final String email, BindingResult result, ModelMap modelMap) {
        boolean verdict = true;
        final String finalPath = utilityServices.getMessage("Application.Url");
        final MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setFrom(preConfiguredMessage.getFrom());
                message.setTo(email);
                message.setSubject("Verify your email to activate DataLife account");

                Template link = velocityEngine.getTemplate("activationMail.vm");

                VelocityContext velocityContext = new VelocityContext();
                UserDetails details = user.getUserDetails();
                if (details != null) {
                    velocityContext.put("firstName", details.getFirstName());
                    velocityContext.put("lastName", details.getLastName());
                }
                ClinicInfo info = user.getClinicInfo();
                if (info != null) {
                    velocityContext.put("firstName", info.getClinicName());
                    velocityContext.put("lastName", "");
                }

                velocityContext.put("activation", finalPath+"/activate/" + user.getUserName() + "/" + user.getUuid());


                StringWriter stringWriter = new StringWriter();

                link.merge(velocityContext, stringWriter);

                message.setText(stringWriter.toString());

            }
        };
        try {
            mailSender.send(preparator);
        } catch (Exception e) {
            e.printStackTrace();
            verdict = false;
        }
        return verdict;
    }

    public void sendToGetDocuments(final User user) {
        final String finalPath = utilityServices.getMessage("Application.Url");
        final MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setFrom(preConfiguredMessage.getFrom());
                message.setTo(user.getUserContactInfo().getEmail());
                message.setSubject("Verify your email to activate DataLife account");

                Template link = velocityEngine.getTemplate("documents.vm");

                VelocityContext velocityContext = new VelocityContext();
                UserDetails details = user.getUserDetails();
                if (details != null) {
                    velocityContext.put("firstName", details.getFirstName());
                    velocityContext.put("lastName", details.getLastName());
                }
                ClinicInfo info = user.getClinicInfo();
                if (info != null) {
                    velocityContext.put("firstName", info.getClinicName());
                    velocityContext.put("lastName", "");
                }

                StringWriter stringWriter = new StringWriter();

                link.merge(velocityContext, stringWriter);

                message.setText(stringWriter.toString());

            }
        };
        try {
            mailSender.send(preparator);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  /*  @Async*/
    public boolean sendUserNameAndPassword(final String name,final String email,final String username,final String pwd, BindingResult result, ModelMap modelMap) {
        final MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setFrom(preConfiguredMessage.getFrom());
                message.setTo(email);
                message.setSubject("DataLife EMR account credentials");

                Template link = velocityEngine.getTemplate("clinicDoctorActivation.vm");
                VelocityContext velocityContext = new VelocityContext();

                if (name != null) {
                    velocityContext.put("name",name);
                }

                velocityContext.put("UserName", username);
                velocityContext.put("Password", pwd);

                StringWriter stringWriter = new StringWriter();

                link.merge(velocityContext, stringWriter);

                message.setText(stringWriter.toString());

            }
        };
        try {
            mailSender.send(preparator);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }



    /**
     * This method is for sending Activation Link or Activation code to registered mail after registration is done
     *
     * @param user
     * @param result
     * @param modelMap
     * @return
     */
    /*@Async*/
    public boolean sendActivationCode(final User user, BindingResult result, ModelMap modelMap) {

        final MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setFrom(preConfiguredMessage.getFrom());
                message.setTo(user.getUserContactInfo().getEmail());
                message.setSubject("DataLife EMR activation code");

                Template code = velocityEngine.getTemplate("activationCode.vm");
                VelocityContext velocityContext = new VelocityContext();
                UserDetails details = user.getUserDetails();
                if (details != null) {
                    velocityContext.put("firstName", details.getFirstName());
                    velocityContext.put("lastName", details.getLastName());
                }
                ClinicInfo info = user.getClinicInfo();
                if (info != null) {
                    velocityContext.put("firstName", info.getClinicName());
                    velocityContext.put("lastName", "");
                }

                StringWriter stringWriter = new StringWriter();

                if (user.getActivation().equals("mobile")) {
                    velocityContext.put("otp", user.getOtp());
                } else {
                    velocityContext.put("otp", user.getOtp());
                }

                code.merge(velocityContext, stringWriter);

                message.setText(stringWriter.toString());

            }
        };
        try {
            mailSender.send(preparator);
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }


    /**
     * @param email
     * @param uuid
     * @param userName
     */
  /* @Async*/
    public boolean forgotPassword(final String email, final String uuid, final String userName) {
        final String finalMessage = utilityServices.getMessage("User.forgotPassword.Message");

        final MimeMessagePreparator preparator = new MimeMessagePreparator() {

            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setFrom(preConfiguredMessage.getFrom());
                message.setTo(email);
                message.setSubject("DataLife EMR password reset information");
                Template link = velocityEngine.getTemplate("forgotPassword.vm");

                VelocityContext velocityContext = new VelocityContext();
                velocityContext.put("flName", userName);
                velocityContext.put("forgotPwdLink", finalMessage + uuid);

                StringWriter stringWriter = new StringWriter();
                link.merge(velocityContext, stringWriter);
                message.setText(stringWriter.toString());

            }
        };
        try {
            mailSender.send(preparator);
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    /*@Async*/
    public void sendRecord(final Long userId, final List<String> fileNames, final String[] emails, final String[] reports) {

        final MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                message.setFrom(preConfiguredMessage.getFrom());
                message.setSubject("Received generated report from DataLife EMR");

                Template link = velocityEngine.getTemplate("pdfReport.vm");
                VelocityContext velocityContext = new VelocityContext();
                if (userId != null) {
                    System.out.println(emails.length);
                    if (emails.length == 1) {
                        message.setTo(emails[0]);
                    } else {
                        message.setTo(emails);
                    }
                }

                String path = utilityServices.getMessage("Encounter.share.download");

                for (String fileName : fileNames) {
                    FileSystemResource file = new FileSystemResource(path + fileName);
                    message.addAttachment(fileName, file);
                }

                StringWriter stringWriter = new StringWriter();

                link.merge(velocityContext, stringWriter);

                message.setText(stringWriter.toString());

            }
        };
        mailSender.send(preparator);

    }

    public void encryptPDF(String src, String dest, String reportPassword) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        String USER_PASS = reportPassword;
        String OWNER_PASS = "datalife";
        stamper.setEncryption(USER_PASS.getBytes(), OWNER_PASS.getBytes(), PdfWriter.ALLOW_COPY, PdfWriter.ENCRYPTION_AES_128);
        stamper.close();
    }

   /* @Async*/
    public void sendAppointmentConfirmation(final String doctorName, final String clinicName, final String mobile, final String address, final String date, final User patInfo) {
        final MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setFrom(preConfiguredMessage.getFrom());
                message.setTo(patInfo.getEmail());
                message.setSubject(clinicName + " - Appointment Scheduled");
                Template link = velocityEngine.getTemplate("confirmPatientAppt.vm");

                VelocityContext velocityContext = new VelocityContext();

                System.out.print(" context path " + servletContext.getContextPath());

                velocityContext.put("patName", patInfo.getFlname());
                velocityContext.put("docName", doctorName);
                velocityContext.put("dateAndTime", date);
                velocityContext.put("clinicPhone", mobile);
                if(StringUtils.isNotBlank(address)){
                    velocityContext.put("clinicAddress", address);
                }else{
                    velocityContext.put("clinicAddress", "________");
                }
                velocityContext.put("clinicName", clinicName);

                StringWriter stringWriter = new StringWriter();

                link.merge(velocityContext, stringWriter);

                message.setText(stringWriter.toString());

            }
        };
        mailSender.send(preparator);

    }

    /*@Async*/
    public void updateStatusToScheduledPatient(final String doctorName, final String clinicPhone, final String clinicName, final int tokenNo, final int curTokenNo, final User user) {
        final String url = utilityServices.getMessage("domain.url");
        final MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setFrom(preConfiguredMessage.getFrom());
                message.setTo(user.getEmail());
                message.setSubject(clinicName + " - Appointment Status update");
                Template link = velocityEngine.getTemplate("updateStatus.vm");

                VelocityContext velocityContext = new VelocityContext();
                velocityContext.put("name", user.getFlname());
                velocityContext.put("doctorName", doctorName);
                velocityContext.put("tokenNo", tokenNo);
                velocityContext.put("curtoken", curTokenNo);
                velocityContext.put("clinicPhone", clinicPhone);
                velocityContext.put("datalifeLink", url);
                StringWriter stringWriter = new StringWriter();
                link.merge(velocityContext, stringWriter);
                message.setText(stringWriter.toString());
            }
        };
        mailSender.send(preparator);
    }

    /*@Async*/
    public void sendCancelConfirmation(final String doctorName, final String clinicName, final String clinicPhone, final String date, final String patientName, final String toEmailId) {
        final String url = utilityServices.getMessage("domain.url");
        final MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setFrom(preConfiguredMessage.getFrom());
                message.setTo(toEmailId);
                message.setSubject(clinicName + " - Appointment Cancellation");
                Template link = velocityEngine.getTemplate("CancellationInfoToPat.vm");

                VelocityContext velocityContext = new VelocityContext();

                System.out.print(" context path " + servletContext.getContextPath());

                velocityContext.put("patientName", patientName);
                velocityContext.put("doctorName", doctorName);
                velocityContext.put("dateAndTime", date);
                velocityContext.put("datalifeLink", url);
                velocityContext.put("clinicPhone", clinicPhone);
                StringWriter stringWriter = new StringWriter();

                link.merge(velocityContext, stringWriter);

                message.setText(stringWriter.toString());

            }
        };
        mailSender.send(preparator);
    }

    /*@Async*/
    public void infoToPatientOnDoctorCancelslot(final String doctorName, final String clinicName, final String clinicPhone, final String date, final String patientName, final String toEmailId) {
        final String url = utilityServices.getMessage("Application.Url");
        final MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setFrom(preConfiguredMessage.getFrom());
                message.setTo(toEmailId);
                message.setSubject(clinicName + " - Appointment Cancellation");
                Template link = velocityEngine.getTemplate("InfoToPatientOnDoctorCancelSlots.vm");

                VelocityContext velocityContext = new VelocityContext();

                System.out.print(" context path " + servletContext.getContextPath());

                velocityContext.put("patientName", patientName);
                velocityContext.put("doctorName", doctorName);
                velocityContext.put("dateandTime", date);
                velocityContext.put("clinicPhone", clinicPhone);
                velocityContext.put("datalifeLink", url);
                StringWriter stringWriter = new StringWriter();

                link.merge(velocityContext, stringWriter);

                message.setText(stringWriter.toString());

            }
        };
        mailSender.send(preparator);
    }

    /*@Async*/
    public void infoToPatientOnDoctorCancelDays(final String doctorName, final String clinicName, final String clinicPhone, final String date, final String patientName, final String toEmailId) {
        final String url = utilityServices.getMessage("Application.Url");
        final MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setFrom(preConfiguredMessage.getFrom());
                message.setTo(toEmailId);
                message.setSubject(clinicName + " - Appointment Cancellation");
                Template link = velocityEngine.getTemplate("InfoToPatientOnDoctorCancelDays.vm");

                VelocityContext velocityContext = new VelocityContext();

                System.out.print(" context path " + servletContext.getContextPath());

                velocityContext.put("patientName", patientName);
                velocityContext.put("doctorName", doctorName);
                velocityContext.put("date", date);
                velocityContext.put("clinicPhone", clinicPhone);
                velocityContext.put("datalifeLink", url);
                StringWriter stringWriter = new StringWriter();

                link.merge(velocityContext, stringWriter);

                message.setText(stringWriter.toString());

            }
        };
        mailSender.send(preparator);
    }

    /*@Async*/
    public boolean verifyEmail(final String email, BindingResult result, ModelMap modelMap) {
        boolean verdict = true;
        final MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setFrom(preConfiguredMessage.getFrom());
                message.setTo(email);
                message.setSubject("DataLife e-mail verification");

                Template link = velocityEngine.getTemplate("verifyEmail.vm");

                VelocityContext velocityContext = new VelocityContext();

                StringWriter stringWriter = new StringWriter();

                link.merge(velocityContext, stringWriter);

                message.setText(stringWriter.toString());

            }
        };
        try {
            mailSender.send(preparator);
        } catch (Exception e) {
            e.printStackTrace();
            verdict = false;
        }
        return verdict;
    }

    /*@Async*/
    public void serviceRequestConfirmation(final String email, final String name, final Long orderId, final String type) {
        final String url = utilityServices.getMessage("Application.Url");
        final MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setFrom(preConfiguredMessage.getFrom());
                message.setTo(email);
                message.setSubject("ServiceRequest Confirmation");
                Template link = velocityEngine.getTemplate("serviceRequestSuccess.vm");

                VelocityContext velocityContext = new VelocityContext();
                velocityContext.put("patientName",name);
                velocityContext.put("serviceRequest", type);
                velocityContext.put("orderId", orderId);
                velocityContext.put("datalifeLink", url);
                StringWriter stringWriter = new StringWriter();
                link.merge(velocityContext, stringWriter);
                message.setText(stringWriter.toString());
            }
        };
        mailSender.send(preparator);
    }

    public boolean sendEmailActivationLink(final User user, final String email) {
        boolean verdict = true;
        final String finalPath = utilityServices.getMessage("Application.Url");
        final MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setFrom(preConfiguredMessage.getFrom());
                message.setTo(email);
                message.setSubject("Verify your email to activate DataLife account");

                Template link = velocityEngine.getTemplate("activationMail.vm");

                VelocityContext velocityContext = new VelocityContext();
                UserDetails details = user.getUserDetails();
                if (details != null) {
                    velocityContext.put("firstName", details.getFirstName());
                    velocityContext.put("lastName", details.getLastName());
                }
                ClinicInfo info = user.getClinicInfo();
                if (info != null) {
                    velocityContext.put("firstName", info.getClinicName());
                    velocityContext.put("lastName", "");
                }

                velocityContext.put("activation", finalPath+"/activate/" + user.getUserName() + "/" + user.getUuid());


                StringWriter stringWriter = new StringWriter();

                link.merge(velocityContext, stringWriter);

                message.setText(stringWriter.toString());

            }
        };
        try {
            mailSender.send(preparator);
        } catch (Exception e) {
            e.printStackTrace();
            verdict = false;
        }
        return verdict;
    }


   /* @Async
    public boolean sendUserNameAndPassword(final String name,final String email,final String username,final String pwd, BindingResult result, ModelMap modelMap) {
        final MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setFrom(preConfiguredMessage.getFrom());
                message.setTo(email);
                message.setSubject("DataLife EMR account credentials");

                Template link = velocityEngine.getTemplate("clinicDoctorActivation.vm");
                VelocityContext velocityContext = new VelocityContext();

                if (name != null) {
                    velocityContext.put("name",name);
                }

                velocityContext.put("UserName", username);
                velocityContext.put("Password", pwd);

                StringWriter stringWriter = new StringWriter();

                link.merge(velocityContext, stringWriter);

                message.setText(stringWriter.toString());

            }
        };
        try {
            mailSender.send(preparator);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
*/
   @Override
   public void run() {
       // TODO Auto-generated method stub

   }
}

