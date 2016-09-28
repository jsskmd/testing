<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script src="resources/jsplugins/build/intlTelInput.min.js"></script>


<div class="form-wrapper confirmAppForm class-popup" id="confirmAppForm" style="max-width: 700px !important;">
<div class="cancel-common"></div>
<div class="confirmpadding" id="tmm-form-wizard">
<div class="note"> </div>
<div class="form-wrapper">
<div class="confirmappointemnt">
    Clinic Profile
</div>
<div>

    <div class="basicdetails-modal list-pge" id="view_clinicProfile">
        <div class="doctordetails-modal ">
            <div class="dcolumn-3">
                <div id="confirmMessage"></div>
                <div class="padding-list">
                    <div class="col-md-12 no-pad form-wizard stnc">
                        <div class="col-md-12">
                            <div class="col-md-6 no-pad alignright" id="clinicUserName">
                            </div>
                            <div class="col-md-6 no-pad alignrightbtn" id="editClinicProfile">
                                <a class="popup-btn"><i class="icon-edit"></i></a>
                            </div>
                        </div>

                        <div class="col-md-12 aligntop">
                            <div class="col-md-12 no-pad">
                                <ul>
                                    <li class="padding-list-icon details">Details</li>
                                    <li class="padding-list-icon"><span class="" id="address"></span> <span class="" id="location">  </span> <span class="" id="city">  </span> <span class="" id="state">  </span><span class=""> <span  id="country"></span>  -&nbsp;<span id="zipCode"></span></span></li>
                                    <li class="padding-list-icon"> <span class="wcp">E-mail</span><span class=""> <span class="index-span"> : </span><span id="email"></span><a id="verify_email">Edit & Verify</a></span></li>
                                    <li class="padding-list-icon"><span class="wcp">Mobile</span><span class=""> <span class="index-span"> : </span><span id="mobilePhone"></span><a id="verify_mobile">Edit & Verify</a></span></li>
                                    <li class="padding-list-icon"><span class="wcp">Phone</span><span class=""> <span class="index-span"> : </span><span id="homePhone"></span></span></li>
                                </ul>
                            </div>
                            <div class="col-md-12 no-pad">

                                <ul class="">
                                    <li class="padding-list-icon"><span class="wcp">Id</span><span class=""> <span class="index-span"> : </span><span id="clinicUserId"></span></span></li>

                                    <li class="padding-list-icon"><span class="wcp">MRN</span><span class=""> <span class="index-span"> : </span><span id="mrnNumber"></span></span></li>

                                </ul>
                            </div>
                        </div>


                    </div>
                </div>
                <ul>
                    <li></li>
                    <li></li>
                    <li></li>
                </ul>
            </div>
            <div class="clear"></div>
        </div>
    </div>
    <div class="clear"></div>


    <div class="basicdetails-modal spce hide"  id="edit_clinicProfile">
        <form:form  id="clinicProfileForm" method="post" commandName="user">
            <input type="hidden" name="userContactInfo.mobilePhone" id="phone" value=""/>
            <input type="hidden" name="userContactInfo.email" id="t_email" value=""/>
            <div class="hide-info">
                <div class="row">

                    <div class="col-md-4 no-pad ">
                        <fieldset class="input-block">
                            <label class="select-records"> Clinic Name<span style="color:red;">*</span></label>
                            <input type="text"  placeholder="Clinic Name" required="" title=" Clinic Name " id="t_clinicName" name="clinicInfo.clinicName"  maxlength="30" pattern="^[A-Za-z ]+$"/>
                            <!--/ .dropdown-->
                        </fieldset>
                    </div>

                    <div class="col-md-4 no-pad ">
                        <fieldset class="input-block">
                            <label class="select-records">Reg./Lic Number<span style="color:red;">*</span></label>
                            <input type="text"  placeholder="Reg./Lic Number" readonly title=" Reg./Lic Number " id="t_mrnNumber" name="clinicInfo.mlrNumber" required="">
                            <!--/ .dropdown-->
                        </fieldset>
                    </div>
                    <div class="col-md-4 no-pad">

                        <fieldset class="input-block">
                            <label class="select-records">Country<span style="color:red;">*</span></label>
                            <div class="dropdown">

                                <form:select required="required" id="t_country" path="userContactInfo.country" name="thelist"
                                             onChange="combo('t_country','t_state','t_stateInput');"
                                             cssClass="dropdown-select"></form:select>
                            </div>
                        </fieldset></div>
                    <!--/ .tooltip-->
                </div>


                <div class="row">
                    <div class="col-md-4 no-pad" id="dt_state">
                        <fieldset class="input-block">
                            <label class="select-records">State</label>
                            <div class="dropdown">
                                <form:select path="userContactInfo.state" id="t_state" title="State Name"
                                             cssClass="dropdown-select"></form:select>

                            </div>
                        </fieldset></div>
                    <div class="col-md-4 no-pad hide" id="dt_stateInput">

                        <fieldset class="input-block">
                            <label class="select-records">State</label>
                            <input type="text" name="userContactInfo.otherState" id="t_stateInput"
                                   placeholder="State Name" title="Id Number"  maxlength="20" pattern="^[a-zA-Z ]+$"/>
                        </fieldset></div>


                    <div class="col-md-4 no-pad ">
                        <fieldset class="input-block">
                            <label class="select-records">City<span style="color:red;">*</span></label>
                            <input type="text"  placeholder="City" required="" title="City " id="t_city" name="userContactInfo.city"  maxlength="13" pattern="^[a-zA-Z ]+$"/>
                            <!--/ .dropdown-->
                        </fieldset>
                    </div>
                    <div class="col-md-4 no-pad">

                        <fieldset class="input-block">
                            <label class="select-records">Address<span style="color:red;">*</span></label>
                            <input type="text"  placeholder="Address" required="" title="Address " id="t_address" name="userContactInfo.address" maxlength="50" />
                        </fieldset></div>
                    <!--/ .tooltip-->
                </div>

                <div class="row">
                    <div class="col-md-4 no-pad">

                        <fieldset class="input-block">
                            <label class="select-records">Location<span style="color:red;">*</span></label>
                            <input type="text"  placeholder="Location" required="" title=" Location" id="t_location" name="userContactInfo.location" maxlength="20" pattern="^[a-zA-Z ]+$"/>
                        </fieldset></div>
                    <!--/ .tooltip-->
                    <div class="col-md-4 no-pad ">
                        <fieldset class="input-block">
                            <label class="select-records">Zip Code<span style="color:red;">*</span></label>
                            <input type="text"  placeholder="Zip Code" required="" title=" Zip Code " id="t_zipCode" name="userContactInfo.zipCode" maxlength="7" pattern="^[\d]{6,7}"/>
                            <!--/ .dropdown-->
                        </fieldset>
                    </div>
                    <!--/ .tooltip-->
                    <div class="col-md-4 no-pad ">
                        <fieldset class="input-block">
                            <label class="select-records">Work phone<span style="color:red;">*</span></label>
                            <input type="text"  placeholder="Work phone" required="" title=" Work phone " id="t_homePhone" name="userContactInfo.homePhone" maxlength="13" pattern="^[\+?\d]{10,13}"/>
                            <!--/ .dropdown-->
                        </fieldset>
                    </div>

                </div>

                <div class="assign-space" style="padding-top: 10px">
                    <input type="button" value="Cancel" class="saveb editbasicinfo" id="cancelprofile">
                    <button type="submit" class="saveb editbasicinfo-vitals" id="saveClinicProfile">Save</button>

                </div>

            </div>
        </form:form>
    </div>


    <div class="clear"></div>


</div>

</div>
<div class="clear"></div>

</div>
</div>


<script type="text/javascript" src="resources/js/server/clinicProfile.js"></script>