<!doctype html>
<html>
<head>
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>DataLife</title>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/style.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/home/bootstrap.min.css">
    <%--
        <link rel="stylesheet" href="/resources/css/home/animate.css">
    --%>

    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jquery/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jsplugins/jquery.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jsplugins/javascript.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jquery/scripts.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jsplugins/bootstrap.min.js"></script>

    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jsplugins/jquery.bxslider.min.js"></script>

    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/jsplugins/flexislider.js"></script>
    <!--testimonial sliders-->

    <script src="<%=request.getContextPath()%>/resources/jsplugins/home/jquery.scrollTo-min.js"></script>
    <script src="<%=request.getContextPath()%>/resources/jsplugins/home/script.js"></script>
    <script src="<%=request.getContextPath()%>/resources/jsplugins/home/jquery.backTop.min.js"></script>
    <script src="<%=request.getContextPath()%>/resources/jsplugins/home/wow.min.js"></script>

</head>

<body>
<header>
    <div class="call_us_demo">
        <div class="container">
            <div class="col-md-6 call_us"> <span class="call_us">Call Us Today!  944-981-1444 |</span> <a href="#" class="support_mail">support@datalife.in</a> </div>
        </div>
    </div>
</header>

<div class="">
    <div class="wsmenuexpandermain slideRight"><a id="navToggle" class="animated-arrow slideLeft" href="#"><span></span></a></div>
    <div class="wsmenucontent overlapblackbg"></div>
    <div class="">
        <div class="container-demo-index">
            <div class="container no-padding">
                <div class="logo_specification">
                    <div class="logo postion"> <a href="/"> <img src="<%=request.getContextPath()%>/resources/images/datalifelogo.png" alt="datalife"> </a></div>
                </div>
                <div class="login-btn">
                    <a href="/login" class=" login_demo btn btn-success btn-14 " id="display_login"> <i class="icon-login hideicon"></i> Login /Register</a>
                </div>
            </div>
        </div>
    </div>

</div>
<section>
    <div id="nav" class="affix-top">
        <div class="nav-width add_property ">
            <div class="">
                <!--Menu HTML Code-->
                <div id="">
                    <nav class="wsmenu slideLeft clearfix" id="sses2">
                        <ul class="mobile-sub wsmenu-list homepage_class" id="dashboard_menu">

                            <li><a class="" href="#"><i class="icon-home-1 hideicon"></i>HOME</a></li>
                            <li><a   href="#article1"><i class="icon-info-1 hideicon"></i>ABOUT </a></li>
                            <li><a   href="#article2"><i class="icon-user-2  hideicon"></i> SUBSCRIBER </a></li>
                            <li><a   href="#article3"><i class="icon-star hideicon"></i>PREMIUM SERVICES </a></li>
                            <li><a  href="#article4"><i class="icon-user-md hideicon"></i>DOCTOR / CLINIC</a>
                            </li>
                            <li><%--<a   href="#article5">--%><a href="#article5"<%-- onclick="window.open('/service/providerHome')"--%>><i class="icon-quote-left hideicon"></i>BUSINESS PARTNERS </a></li>
                            <!-- <li><a id=""  href="#article6">MEMBERSHIP PLANS </a>-->

                            <%-- <li class="hide_login_btn login_demo" style="display:none"><a href="/login">LOGIN/REGISTER</a>
                               </li>--%>
                        </ul>
                    </nav></div>
            </div>
        </div>
        <!--Menu HTML Code-->
    </div>
</section>
<section>
    <div class="section_1 " >
        <div class="sect_background">
            <div class="container">
                <div class="home-banner">
                    <h1 class="new_lab_info wow fadeInUp" data-wow-duration="1s" data-wow-delay="0s">Your Health <span>Data</span>, Your Healthy <span class="life">Life</span></h1>
                    <div class="exclusive_health">
                        <h3 class="address_info wow fadeInUp" data-wow-duration="1s" data-wow-delay="0s">An exclusive healthcare platform
                            for clinics, doctors & patients </h3>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<section id="article1" >
    <div class="container wow fadeInUp" data-wow-duration="1s" data-wow-delay="0s">
        <div class="about-datalife">
            <h3 class="about_data">ABOUT DATALIFE</h3>
            <div class="titleline for_sr_page"></div>
            <p class="data_life_intro">Datascribe is a Bangalore based STPI registred ISO 9001 certified company and since 1999, has earned the trust of thousands of Doctors and millions of patients in US and Canada. Datascribe is all set to extend its services of Electronic Medical Records and Healthcare Services to Indian doctors and patients through DataLife.</p>
        </div>
    </div>
</section>

<section id="article2" >
    <div class="find_doc_demo">
        <h3 class="about_data for_sub_patient">FOR <span class="heading_demo_page">SUBSCRIBERS</span> / PATIENTS</h3>
        <div class="pre_service_line"></div>
    </div>
    <div class="subscriber_links">
        <div class="container">
            <div class="col-md-4 no-pad wow fadeInDown " data-wow-duration="1s" data-wow-delay="0s">
                <div class="find_doc_fix "> <div class="sprite-for_patients patient1 patientsprite"></div>
                    <h4 class="find_doc">FIND A DOCTOR TO  GET AN APPOINTMENT</h4>
                    <P class="emr_rec apply_poprerty">DataLife enables you to find specialty doctors in your city and pick suitable slot for the appointments online.</P>
                </div>
            </div>
            <div class="col-md-4 no-pad wow fadeInDown " data-wow-duration="1s" data-wow-delay="0s">
                <div class=" find_doc_fix "> <div class="sprite-for_patients patient1 patientsprite1"></div>
                    <h4 class="upload_document">UPLOAD PRESCRIPTION & REPORTS</h4>
                    <p class="emr_rec apply_poprerty">
                        To have continuity of health records chronologically, upload all your existing paper records which can be viewed by doctor during your next visit such as prescriptions, lab reports, X-rays, MRIs, etc.

                    </p>
                </div>
            </div>
            <div class="col-md-4 no-pad wow fadeInDown " data-wow-duration="1s" data-wow-delay="0s">
                <div class=" find_doc_fix "><div class="sprite-for_patients patient1 patientsprite2"></div>
                    <h4 class="maintain_rec">UPDATE E-RECORDS FOR EVERY VISIT</h4>
                    <p class="emr_rec apply_poprerty">Every clinic visit is monitored, updated with medical information by your doctor.</p>
                </div>
            </div>
        </div>

        <div class="container">
            <div class="col-md-4 no-pad wow fadeInDown " data-wow-duration="1s" data-wow-delay="0s">
                <div class="find_doc_fix "> <div class="sprite-for_patients patient1 patientsprite3"></div>
                    <h4 class="find_doc send_report">SEND REPORTS TO REFERRAL DOCTORS</h4>
                    <p class="emr_rec apply_poprerty">Visit summary medical information filled by the doctor is formed into a summary report which is ready to be sent to referral doctors by e-mail</p>
                </div>
            </div>
            <div class="col-md-4 no-pad wow fadeInDown " data-wow-duration="1s" data-wow-delay="0s">
                <div class=" find_doc_fix "> <div class="sprite-for_patients patient1 patientsprite4"></div>
                    <h4 class="upload_document vitals">MONITOR YOUR VITALS</h4>
                    <p class="emr_rec apply_poprerty">At times doctor may ask to keep watch and record the vitals such as blood pressure, temperature and sugar which can be recorded easily by yourself</p>
                </div>
            </div>
            <div class="col-md-4 no-pad wow fadeInDown " data-wow-duration="1s" data-wow-delay="0s">
                <div class=" find_doc_fix "> <div class="sprite-for_patients patient1 patientsprite5"></div>
                    <h4 class="maintain_rec family">FAMILY AND MEDICAL HISTORY</h4>
                    <p class="emr_rec apply_poprerty">It is important for proper diagnosis and treatment to know the precious family and medical history such as smoking, alcohol, food habits and previous medications, etc.</p>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="col-md-4 no-pad wow fadeInDown " data-wow-duration="1s" data-wow-delay="0s">
                <div class="find_doc_fix "> <div class="sprite-for_patients patient1 patientsprite6"></div>
                    <h4 class="find_doc send_report medical">MEDICAL ALLERGIES</h4>
                    <p class="emr_rec apply_poprerty">DataLife ensures the patient to record drug, medication or food allergies, etc., so that Doctor is always informed about allergies to prescribe the right medication.
                    </p>
                </div>
            </div>
            <div class="col-md-4 no-pad wow fadeInDown " data-wow-duration="1s" data-wow-delay="0s">
                <div class=" find_doc_fix "> <div class="sprite-for_patients patient1 patientsprite7"></div>
                    <h4 class="upload_document wellness">WELLNESS GUIDELINES</h4>
                    <p class="emr_rec apply_poprerty">Doctor gives advice to the patient and family on precautions to be taken related to lifestyle, food, exercises and alternative medicine such as yoga practices, etc.</p>
                </div>
            </div>
            <div class="col-md-4 no-pad wow fadeInDown " data-wow-duration="1s" data-wow-delay="0s">
                <div class=" find_doc_fix "> <div class="sprite-for_patients patient1 patientsprite8"></div>
                    <h4 class="maintain_rec internal">INTERNAL MESSAGING WITH DOCTOR</h4>
                    <p class="emr_rec apply_poprerty">When you want to check with the doctor regarding continuation of medication or increase or decrease of dosage or sudden developments of allergies, etc.</p>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="col-md-4 no-pad wow fadeInDown " data-wow-duration="1s" data-wow-delay="0s">
                <div class="find_doc_fix "><div class="sprite-for_patients patient1 patientsprite9"></div>
                    <h4 class="find_doc bill">MEDICAL BILLS</h4>
                    <p class="emr_rec apply_poprerty">You can keep track of all the bills paid towards consultation, diagnostic tests, hospitalization bills, medications, etc., at one place and use for insurance claims if any.</p>
                </div>
            </div>
            <div class="col-md-4 no-pad wow fadeInDown " data-wow-duration="1s" data-wow-delay="0s">
                <div class=" find_doc_fix "> <div class="sprite-for_patients patient1 patientsprite10"></div>
                    <h4 class="upload_document hippa">CONFIDENTIALITY & HIPAA COMPLIANT</h4>
                    <p class="emr_rec apply_poprerty">Your health data is secure and confidential with us as we are HIPAA compliant which is a international standard for protecting sensitive patient data which ensures security measures are in place and followed.

                    </p>
                </div>
            </div>
            <div class="col-md-4 no-pad wow fadeInDown " data-wow-duration="1s" data-wow-delay="0s">
                <div class=" find_doc_fix "> <div class="sprite-for_patients patient1 patientsprite11"></div>
                    <h4 class="maintain_rec access_rec">ACCESS RECORDS FROM ANYWHERE </h4>
                    <p class="emr_rec apply_poprerty">
                        Your health data can be accessed from anywhere in the world as our application is cloud based which helps physicians to share or access your records for better healtchare decisions.
                    </p>
                </div>
            </div>
        </div>
    </div>
</section>
<section id="article3">
    <div class="data_premium">
        <div class="dat_background">
            <div class="container add_premiumcolor">
                <div class="col-md-6 wow fadeInLeft" data-wow-duration="1s" data-wow-delay="0s" >
                    <div class=" consult_premium_services">
                        <h4 class="pre_services">Datalife premium services</h4>
                        <div class="pre_service_line"></div>
                        <p class="emr_rec pre_pack_ser">Datalife is not just an EHR/EMR, but a complete healthcare platform providing premium services such as medical second opinion, surgery referral, online pharmacy, labs & diagnostics and non-emergency medical transport to its privileged subscribers.</p>
                        <div class="background_video">
                            <ul class="bxslider">
                                <li><img src="<%=request.getContextPath()%>/resources/images/videoimages.png" alt="youtubelink"/></li>
                                <li><img src="<%=request.getContextPath()%>/resources/images/videoimages1.png" alt="youtubelink"/></li>
                                <li><img src="<%=request.getContextPath()%>/resources/images/videoimages2.png" alt="youtubelink"/></li>


                            </ul>


                        </div>
                    </div>
                </div>
                <div class="col-md-6 add_lines wow fadeInRight" data-wow-duration="1s" data-wow-delay="1s">
                    <div class="content">
                        <div  id="1" class="accordion">
                            <div class="accordion-item active">
                                <div class="accordion-header labs_diagnostics"> pharmacy at your doorstep <span class="accordion-item-arrow up_arrow"></span> </div>
                                <div class="accordion-content " style="display:block">
                                    <div class="col-md-12 add_align">
                                        <p>Datalife pharmacy service is an engaging interface designed for convenience of our subscribers. Users can easily get their medicines by uploading or through the forwarded prescription by the doctor and get their medications delivered to their doorstep by the pharmacist. </p>
                                        <p class="this_req"> This request can be placed from anywhere with just a few clicks and with significant savings.</p>


                                        <a href="#" class="accr_know_more" onclick="window.open('/service/index')">KNOW MORE</a></div>
                                </div>
                            </div>
                        </div>
                        <div id="2" class="accordion">
                            <div class="accordion-item ">
                                <div class="accordion-header labs_diagnostics"> labs/diagnostics made easy <span class="accordion-item-arrow up_arrow"></span> </div>
                                <div class="accordion-content " >
                                    <div class="col-md-12 add_align">
                                        <p>It can be for prescribed diagnostics or regular health checkup. Datalife can help you by providing at-home lab services with significant savings. The labs registered with us provide a wide range of lab tests which includes Blood Urea Nitrogen (BUN), </p>

                                        <p class="lab1" style="display:none !important;"> Complete blood count, Fast Blood Sugar (FBS), Postprandial blood sugar (PPBS), Lipid profile, serum creatinine, uric acid and more. You can request for a lab test online or through prescription from doctor. Samples will be collected at your doorstep and the reports will be uploaded to your records in Datalife.</p>
                                        <a href="#" class="accr_know_more" id="lab_know" onclick="window.open('/service/index')">KNOW MORE</a></div>
                                </div>
                            </div>
                        </div>
                        <div id="3" class="accordion">
                            <div class="accordion-item ">
                                <div class="accordion-header labs_diagnostics"> second opinion doctor  (US, Canada) <span class="accordion-item-arrow up_arrow"></span> </div>
                                <div class="accordion-content">
                                    <div class="col-md-12 add_align">
                                        <p>When patients face difficult health decisions, and look for answers or alternatives, a medical second opinion from an expert can provide them with the confidence to make best decisions for themselves and their loved ones.</p>

                                        <p class="sec_cls" style="display:none !important;">Leading doctors across the globe are brought together on Datalife exclusively to give consultations in specialties which require crucial guidance before finalizing the procedure for treatment with decision clinching and trustworthy medical second opinion.

                                            Datalife enables its premium subscribers to establish medical second opinion with the world's renowned specialists by placing a request through the application or via call to the customer care and get their appointment scheduled with the doctors.</p>
                                        <a href="#" class="accr_know_more" id="second_doctor" onclick="window.open('/service/index')">KNOW MORE</a></div>
                                </div>
                            </div>
                        </div>
                        <div id="4" class="accordion">
                            <div class="accordion-item ">
                                <div class="accordion-header labs_diagnostics"> tele consultation <span class="accordion-item-arrow up_arrow"></span> </div>
                                <div class="accordion-content">
                                    <div class="col-md-12 add_align">
                                        <p>Teleconsult service empowers the subscribers to talk to a doctor in case of health variation and provides assistance with resources to take more control of their health and lead a healthier life. </p>
                                        <p class="consult_doc" style="display:none;">Consult in minutes. Just pick up the phone, dial the customer care number and get connected to a doctor within minutes. The on call service should be utilized when subscriber is seeking information on medical conditions, want advice or recommendations for common conditions.</p>
                                        <a href="#" class="accr_know_more" id="consult_time" onclick="window.open('/service/index')">KNOW MORE</a></div>
                                </div>
                            </div>
                        </div>
                        <div id="5" class="accordion">
                            <div class="accordion-item">
                                <div class="accordion-header labs_diagnostics"> surgery referral <span class="accordion-item-arrow up_arrow"></span> </div>
                                <div class="accordion-content">



                                    <div class="col-md-12 add_align">
                                        <p>Datalife provides surgery referral service through world renowned surgeons & hospitals in India. This service can be utilized by just calling our customer care or sending a request through our application.</p>

                                        <p class="surgery_content" style="display: none;">Datalife strives towards providing patients with world-class medical treatments and surgical treatment options contributing to the well-being of patients. We cater to cost sensitive patients in india as well as overseas.

                                            Datalife maintains a network of surgeons and accredited hospitals in India which are selected on a number of parameters including experience in specific healthcare segments, panel of surgeons, certifications, infrastructure base and past records.</p>
                                        <a href="#" class="accr_know_more" id="surgery_more" onclick="window.open('/service/index')">KNOW MORE</a></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<section id="article4">
    <div class="about-datalife">
        <h3 class="about_data wow fadeInUp" data-wow-duration="1s" data-wow-delay="0s" >FOR DOCTORS AND CLINICS</h3>
        <div class="titleline for_sr_page wow fadeInUp" data-wow-duration="1s" data-wow-delay="0s"></div>
    </div>
    <div class="container doctor_appointments">
        <div class="col-md-6 remove_pad wow fadeInLeft" data-wow-duration="1s" data-wow-delay="0s">
            <ul>
                <li class="bg_url">
                    <div class="col-md-2">
                        <div class="subscriber_button button1"></div>
                    </div>
                    <div class="col-md-10 add_pad">
                        <h3 class="online_facility">ONLINE APPOINTMENT SCHEDULING</h3>
                        <div class="for_doctor_page"></div>
                        <p class="emr_rec add_doc_details">Manage patient appointments effectively enhancing better service and care to patient bringing efficiency and streamlining your clinic management.
                        </p>
                    </div>
                </li>
                <li class="bg_url">
                    <div class="col-md-2">
                        <div class="subscriber_button button2"></div>
                    </div>
                    <div class="col-md-10 add_pad ">
                        <h3 class="online_facility">CUSTOMIZED MULTI SPECIALTY TEMPLATES</h3>
                        <div class="for_doctor_page"></div>
                        <p class="emr_rec add_doc_details"> Datalife provides multiple specialty templates based on medical specialties and also provides customized templates for alternative medicine to record patient medical record.</p>
                    </div>
                </li>
                <li class="bg_url">
                    <div class="col-md-2">
                        <div class="subscriber_button button3"></div>
                    </div>
                    <div class="col-md-10 add_pad ">
                        <h3 class="online_facility">MEDICAL ALLERGY ALERTS</h3>
                        <div class="for_doctor_page"></div>
                        <p class="emr_rec add_doc_details"> Allergies are sometimes life threatening. DataLife ensures the patient to record different kinds of allergies such as drug, food, & medication allergies so that doctor is always informed about allergies to prescribe right medications
                            .
                        </p>
                    </div>
                </li>
                <li class="bg_url">
                    <div class="col-md-2">
                        <div class=" subscriber_button button4"></div>
                    </div>
                    <div class="col-md-10 add_pad ">
                        <h3 class="online_facility">E-PRESCRIPRTION</h3>
                        <div class="for_doctor_page"></div>
                        <p class="emr_rec add_doc_details">
                            Doctor prescribes the medication using the most recent drug databases with appropriate dosage and frequency etc., which can be easily integrated using HL7 to the existing EMRs or sent to any pharmacy or hospital a click away.

                        </p>
                    </div>
                </li>
                <li class="bg_url">
                    <div class="col-md-2">
                        <div class="subscriber_button button5"></div>
                    </div>
                    <div class="col-md-10 add_pad ">
                        <h3 class="online_facility">CLOUD BASED SYSTEM</h3>
                        <div class="for_doctor_page"></div>
                        <p class="emr_rec add_doc_details">All the clinical records are maintained in cloud based servers ensuring security and confidentiality where in no need of maintaining IT infrastructure and software in the clinics/hospitals. </p>
                    </div>
                </li>
                <li class="bg_url">
                    <div class="col-md-2">
                        <div class="subscriber_button button6"></div>
                    </div>
                    <div class="col-md-10 add_pad ">
                        <h3 class="online_facility">INTERNATIONAL CLASSIFICATION OF DISEASES<span>(ICD 10)</span></h3>
                        <div class="for_doctor_page"></div>
                        <p class="emr_rec add_doc_details"> DataLife has built-in international classification of diseases(ICD-10) for better assessment of health conditions and this helps in exchanging health information across borders.</p>
                    </div>
                </li>
                <li class="bg_url">
                    <div class="col-md-2">
                        <div class="subscriber_button button7"></div>
                    </div>
                    <div class="col-md-10 add_pad ">
                        <h3 class="online_facility">INTERNAL MESSAGING SYSTEM</h3>
                        <div class="for_doctor_page"></div>
                        <p class="emr_rec add_doc_details"> It is a unique messaging system which enables the doctor to communicate with patients to advice on treatment or change of medication.</p>
                    </div>
                </li>
            </ul>
        </div>
        <div class="col-md-6 remove_pad wow fadeInRight" data-wow-duration="1s" data-wow-delay="0s">
            <ul>
                <li class="bg_url">
                    <div class="col-md-2">
                        <div class="subscriber_button button8"></div>
                    </div>
                    <div class="col-md-10 add_pad ">
                        <h3 class="online_facility">DICTATION FACILITY</h3>
                        <div class="for_doctor_page"></div>
                        <p class="emr_rec add_doc_details">We understand it is tedious for physicians to enter manually all the fields in the templates. So we provide a facility to the doctor to dictate using mobile or a tab.</p>
                    </div>
                </li>
                <li class="bg_url">
                    <div class="col-md-2">
                        <div class="subscriber_button button9"></div>
                    </div>
                    <div class="col-md-10 add_pad ">
                        <h3 class="online_facility">COMPREHENSIVE PATIENT SUMMARY REPORT</h3>
                        <div class="for_doctor_page"></div>
                        <p class="emr_rec add_doc_details">
                            Maintaining health records of patients digitally enables doctors to keep the patient
                            health information safe and access health records for more coordinated and efficient
                            patient care. </p>
                    </div>
                </li>
                <li class="bg_url">
                    <div class="col-md-2">
                        <div class="subscriber_button button10"></div>
                    </div>
                    <div class="col-md-10 add_pad ">
                        <h3 class="online_facility">GRAPHICAL PRESENTATION OF VITALS</h3>
                        <div class="for_doctor_page"></div>
                        <p class="emr_rec add_doc_details"> Physicians can view the changes in vitals such as temperature, blood pressure, sugar, &  weight in a graphical presentation over a period.</p>
                    </div>
                </li>
                <li class="bg_url">
                    <div class="col-md-2">
                        <div class="subscriber_button button11"></div>
                    </div>
                    <div class="col-md-10 add_pad ">
                        <h3 class="online_facility">E-LAB ORDERS</h3>
                        <div class="for_doctor_page"></div>
                        <p class="emr_rec add_doc_details">Doctor prescribes the lab order which can be easily integrated using HL7 integration to the existing EMRs or sent to any diagnostics centers or hospitals at a click away.</p>
                    </div>
                </li>
                <li class="bg_url">
                    <div class="col-md-2">
                        <div class="subscriber_button button12"></div>
                    </div>
                    <div class="col-md-10 add_pad ">
                        <h3 class="online_facility">RECORDS FOR CLINIC ONLY</h3>
                        <div class="for_doctor_page"></div>
                        <p class="emr_rec add_doc_details">At times physician prefers not to disclose patient or the family about the conditions. DataLife enables confidentiality of records while keeping the records confined to the clinic only .</p>
                    </div>
                </li>
                <li class="bg_url">
                    <div class="col-md-2">
                        <div class="subscriber_button button13"></div>
                    </div>
                    <div class="col-md-10 add_pad ">
                        <h3 class="online_facility">DECISION MAKING TOOL</h3>
                        <div class="for_doctor_page"></div>
                        <p class="emr_rec add_doc_details">
                            Systematic physical examination and review of system data capturing relating to the ICD considering medical drug allergies helps doctors diagnose and prescribe medicines.
                        </p>
                    </div>
                </li>
                <li class="bg_url">
                    <div class="col-md-2">
                        <div class="subscriber_button button14"></div>
                    </div>
                    <div class="col-md-10 add_pad ">
                        <h3 class="online_facility">PATIENT EDUCATION AND WELLNESS GUIDELINES</h3>
                        <div class="for_doctor_page"></div>
                        <p class="emr_rec add_doc_details"> DataLife enables the doctor educating the patient on do's and dont's for specific health condition and general wellness guidelines for overall well being of the patient.</p>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</section>

<section >
    <div class="container">
        <div class="datalife_subscriber">
            <h3 class="about_data wow fadeInUp " data-wow-duration="1s" data-wow-delay="0s"><span class="heading_demo_page">DATALIFE </span> (EMR) ELECTRONIC MEDICAL RECORDS!</h3>
            <div class="titleline for_sr_page"></div>
            <div class="add_emr_details">
                <p class="emr_rec text_justify wow fadeInUp " data-wow-duration="1s" data-wow-delay="0s">Patients who maintain a complete and up-to-date health
                    records help their doctors to offer quality treatment in minimum time. Lack of patient records means
                    the patient is walking-in as a "black box";
                    Dr. needs to expend precious time in recording all the minute details from profile info, family history,
                    allergies, etc., to carry out basic diagnostic investigation and procedure instead of starting the line of treatment.
                    This consumes lot of time, effort and money. </p>
                <p class="emr_rec text_justify wow fadeInUp " data-wow-duration="1s" data-wow-delay="0s">The issue of lack of proper record keeping is especially true to the Indian population, with a majority not even maintaining paper records. Even with those maintaining paper records, most of the times are difficult to locate, update, and share with others. These records are subject to physical loss and damage. Hence the need for an Electronic Medical Record/Electronic Health Record (EMR/EHR).</p>
                <p class="emr_rec text_justify wow fadeInUp " data-wow-duration="1s" data-wow-delay="0s">Recognizing the importance of Electronic Medical Records worldwide, Govt. of India constituted an expert committee to develop an EMR/EHR standards which produced a draft report now put in public domain for stakeholder review. This is in keeping with the best practices which have become a norm for doctors around the world.</p>
                <%--
                                <div class="align_state"> <a href="#">KNOW MORE</a> <a href="#">WATCH VIDEO</a> </div>
                --%>
            </div>
        </div>
    </div>
</section>
<%--<section >
    <div class="container">
        <ul class="reg_clinic_info">
            <li class="reg_clinic wow fadeInUp" data-wow-duration="1s" data-wow-delay="0s">REGISTER YOUR CLINIC WITH DATALIFE NOW</li>
            <li class="reg_clinic doc_regi wow fadeInUp" data-wow-duration="1s" data-wow-delay="0s">ARE YOU A DOCTOR REGISTER WITH DATALIFE NOW</li>
        </ul>
    </div>
</section>--%>
<%--<section id="article5">
    <div class="container wow fadeInUp " data-wow-duration="1s" data-wow-delay="0s">
        <div  class="testimonials-cont">
            <div class="cd-testimonials-wrapper cd-container">
                <ul class="cd-testimonials">
                    <li>
                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
                        <div class="cd-author">
                            <ul class="cd-author-info">
                                <li>MyName</li>
                                <li>CEO, Datalife</li>
                            </ul>
                        </div>
                    </li>
                    <li>
                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Necessitatibus ea, perferendis error repudiandae numquam dolor fuga temporibus. Unde omnis, consequuntur.</p>
                        <div class="cd-author">
                            <ul class="cd-author-info">
                                <li>MyName</li>
                                <li>Designer, Datalife</li>
                            </ul>
                        </div>
                    </li>
                    <li>
                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quam totam nulla est, illo molestiae maxime officiis, quae ad, ipsum vitae deserunt molestias eius alias.</p>
                        <div class="cd-author">
                            <ul class="cd-author-info">
                                <li>MyName</li>
                                <li>CEO, CompanyName</li>
                            </ul>
                        </div>
                    </li>
                </ul>
                <!-- cd-testimonials -->

            </div>
        </div>
    </div>
</section>--%>
<!--<section id="article6">
  <div class="mem_form">
    <div class="container">
      <h3 class="member_form"> Subscriber Membership Plans</h3>
      <div class="col-md-4 col-sm-4 wow fadeInUp " data-wow-duration="1s" data-wow-delay="0s">
        <div class="add_border">
          <h2 class="std">Standard</h2>
          <h1 class="free">Free </h1>
          <h5 class="per_yr">Per Year</h5>
          <div class="adding_list">
            <ul class="available_offer">
              <li><a href="#">Amazing Bonus</a></li>
              <li><a href="#">Another Amazing Bonus </a></li>
              <li><a href="#">Amazing Bonus</a></li>
              <li><a href="#">Another Amazing Bonus </a></li>
              <li><a href="#">Amazing Bonus</a></li>
              <li><a href="#">Another Amazing Bonus </a></li>
            </ul>
          </div>
          <div class="sign_up_page"> <a href="#" class="sign_up_class">Sign Up Now</a> </div>
        </div>
      </div>
      <div class="col-md-4 col-sm-4 wow fadeInUp" data-wow-duration="1s" data-wow-delay="0s">
        <div class="add_border for_premim_cust">
          <h2 class="premium">Premium</h2>
          <h5 class="most_powerful">MOST POPULAR</h5>
          <div class="premium_rate ">
            <h1 class="free amount_cls premium_payment">Rs 6000 </h1>
            <h5 class="per_yr popular_pack">Per Month</h5>
          </div>
          <div class="adding_list">
            <ul class="available_offer">
              <li><a href="#">Amazing Bonus</a></li>
              <li><a href="#">Another Amazing Bonus </a></li>
              <li><a href="#">Amazing Bonus</a></li>
              <li><a href="#">Another Amazing Bonus </a></li>
              <li><a href="#">Amazing Bonus</a></li>
              <li><a href="#">Another Amazing Bonus </a></li>
            </ul>
          </div>
          <div class="sign_up_page"> <a href="#" class="sign_up_class">Sign Up Now</a> </div>
        </div>
      </div>
      <div class="col-md-4 col-sm-4 wow fadeInUp" data-wow-duration="1s" data-wow-delay="1s">
        <div class="add_border">
          <h2 class="std">Life Time</h2>
          <h1 class="free">RS 5000 </h1>
          <h5 class="per_yr">Life Time Membership</h5>
          <div class="adding_list">
            <ul class="available_offer">
              <li><a href="#">consectetur adipisicing</a></li>
              <li><a href="#">consectetur adipisicing </a></li>
              <li><a href="#">consectetur </a></li>
              <li><a href="#"> adipisicing consectetur </a></li>
              <li><a href="#">consectetur</a></li>
              <li><a href="#">consectetur   </a></li>
            </ul>
          </div>
          <div class="sign_up_page"> <a href="#" class="sign_up_class">Sign Up Now</a> </div>
        </div>
      </div>
    </div>
  </div>
</section>-->
<div class="bottom-footer">
    <div class="container">
        <div class="row">
            <!--Foot widget-->
            <div class="col-xs-12 col-sm-12 col-md-12 foot-widget-bottom">
                <ul class="foot-menu col-xs-12 col-md-7 no-pad">
                    <li><a>Terms &amp; Conditions</a></li>
                </ul>
                <p class="col-xs-12 col-md-5 no-pad text-right ">Copyright 2016 Datalife | All Rights Reserved </p>
            </div>
        </div>
    </div>
</div>
<a id='backTop' >Back To Top</a>

<script>
    $(document).ready(function(){
        $("#know").click(function(){
            /*$(".this_req").slideToggle();
             $(".lab1").hide();
             $(".sec_cls").hide();
             $(".consult_doc").hide();*/
            $('#ajaxDiv').load("/service/index", function (e) {

            });
        });
    });

</script>
<script>
    $(document).ready(function(){
        $("#lab_know").click(function(){
            /* $(".this_req").hide();
             $(".lab1").slideToggle();
             $(".sec_cls").hide();
             $(".consult_doc").hide();*/
            $('#ajaxDiv').load("/service/index", function (e) {

            });

        });
        $('.bxslider').bxSlider({
            auto: true
        });
        $("#second_doctor").click(function(){
            /* $(".this_req").hide();
             $(".lab1").hide();
             $(".sec_cls").slideToggle();
             $(".consult_doc").hide();*/
            $('#ajaxDiv').load("/service/index", function (e) {

            });
        });
        $("#consult_time").click(function(){
            /* $(".this_req").hide();
             $(".lab1").hide();
             $(".sec_cls").hide();
             $(".consult_doc").slideToggle();*/
            $('#ajaxDiv').load("/service/index", function (e) {

            });
        });

        $("#surgery_more").click(function(){
            /* $(".this_req").hide();
             $(".lab1").hide();
             $(".sec_cls").hide();
             $(".surgery_content").slideToggle();*/
            $('#ajaxDiv').load("/service/index", function (e) {

            });
        });

        $("#partner_withus").click(function(){

        });
        $(".labs_diagnostics").hover(function() {
            $(this).find(".up_arrow").css("color","#be335c")
        }).mouseout(function() {
            $(this).find(".up_arrow").css("color","#be335c")
        });
    });
</script>



</body>
</html>
