<!doctype html>
<html>
<head>
    <title>DATALIFE HEALTH CARD</title>
    <link rel = "STYLESHEET"
    type = "text/css"
    href = "../../resources/css/idstyle.css" >

</head>
<body>
<div align="center">
    <center>
        <table border="0" cellpadding="0" cellspacing="0" width="672">
            <tr>
                <td width="505" align="center" height="7"><div align="center">
                    <center>
                        <table border="0" cellpadding="0" cellspacing="0" height="197">
                            <tr>
                                <td valign="top"><div align="center">
                                    <center>
                                        <table width="336" height="197" cellspacing="0" cellpadding="0" style="border:3px solid #000;">
                                            <tbody>
                                            <tr>
                                                <td width="336" valign="top" align="left"><div align="center">
                                                    <center>
                                                        <table width="99%" cellspacing="0" cellpadding="0" border="0">
                                                            <tbody>
                                                            <tr>
                                                                <td width="100%"><div align="center">
                                                                    <center>
                                                                        <p class="image-overflow"><font color="#FF0000"> <img width="100" height="41" border="" align="left" valign="top" src="../../resources/images/idcard/datalifelogo.png">
                                                                            <span class="smtxt"> IDENTITY CARD</span>
                                                                        </font>
                                                                            <font color="#FF0000"> <span class="smtxt dfss">${user.userId}</span>
                                                                            </font>
                                                                        </p>

                                                                    </center>
                                                                </div></td>
                                                            </tr>
                                                            <tr align="center">
                                                                <td width="100%" height="5"></td>
                                                            </tr>
                                                            <tr align="center">
                                                                <td width="100%" bgcolor="#333132" align="left"><img id="getProfilePtientImage" alt="users" src="${imageUrl}" class="back-image">
                                                                    <p><font color="#D6E279" class="user-id-name"> <span class="id-det-per"> Name <span class="naming"> : ${name}</span></span>
                                                                        <span class="id-det-per"> Blood Group <span class="grp">: ${bloodGroup}</span></span>
                                                                        <span class="id-det-per list"> Emergency Co.No <span class="num-in">: ${number}</span></span>
                                                                    </font></p></td>
                                                            </tr>

                                                            </tbody>
                                                        </table>
                                                    </center>
                                                </div></td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </center>
                                </div></td>
                                <td align="center" valign="top"><div align="center">
                                    <center>
                                        <table width="336" height="100%" cellspacing="0" cellpadding="0" style="border:3px solid #000;height:196px;">
                                            <tbody>

                                            <tr>
                                                <td width="336" valign="top" align="left"><div align="center">
                                                    <center>
                                                        <table width="99%" cellspacing="0" cellpadding="0" border="0">
                                                            <tbody>
                                                            <tr>
                                                                <td width="100%" align="center"><font color="#3F3F3F"> In case of medical emergency, for subscriber information, please contact</font></td>
                                                            </tr>
                                                            <tr>
                                                                <td width="100%" height="6"></td>
                                                            </tr>
                                                            <tr>
                                                                <td width="100%"><div align="center">
                                                                    <center>
                                                                        <p class="allign-demo"><font color="#FF0000"> <img width="41" height="41" border="" align="left" class="imag-for-ic" src="../../resources/images/idcard/phone-symbolic.png">
                                               <span class="smtxt demo"> +080-2235-9008 <br>
                                                 +91-94498-94498 </span></font></p>
                                                                    </center>
                                                                </div>
                                                                    <div align="center">
                                                                        <center>
                                                                            <p class="allign-demo"><font color="#FF0000"> <img width="41" height="41" border="" align="left" class="imag-for-ic" src="../../resources/images/idcard/mail-ico.png">
                                               <span class="smtxt demo">support@datalife.in<br>
                                                subscribe@datalife.in</span></font></p>
                                                                        </center>
                                                                    </div>
                                                                </td>
                                                            </tr>

                                                            <td width="100%" align="center"><font color="#8F8F8F"> Disclaimer: Data appearing on the card is as per the patient database available in datalife.in</font>
                                                            </td>
                                                            <tr>
                                                                <td width="100%" align="center"><font color="#C74E25"> Card Issue Date: ${user.creationDate}.</font></td>
                                                            </tr>

                                                            </tbody>

                                                        </table>
                                                    </center>
                                                </div></td>
                                            </tr>
                                            </tbody>

                                        </table>
                                    </center>
                                </div></td>
                            </tr>
                        </table>
                    </center>
                </div></td>
            </tr>
            <tr align="center">
                <td width="644" valign="top" height="20" align="center" colspan="2"><center>
                    <br>
                    --------------------- Cut around outer edge of above card; fold in half; laminate for durability ------------------- <br>
                    <p></p>
                    <center>
                        Front Side&nbsp; &nbsp;<font color="#CC0000&quot;">.....Generate this  3 - 1/2 X 2 inch card* ..... </font> &nbsp;  &nbsp;Back Side
                        <p></p>

                        <p> <br>
                            <br>
                            <br>
                            <br>
                        </p>
                    </center>
                </center></td>
            </tr>
        </table>
    </center>
</div>
</body>
</html>