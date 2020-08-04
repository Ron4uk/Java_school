<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <link rel="stylesheet" href="/webjars/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <title>MyJSPPage</title>


</head>
<body>
<script language="Javascript">

</script>

<div class="container" style="margin-bottom: 10px">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <span class="navbar-brand" href="#">
        <img src="/images/label.jpg" width="30" height="30"
             class="d-inline-block align-top" alt="" loading="lazy">
        Mokia
    </span>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="/startauthempl">Main <span class="sr-only">(current)</span></a>
                </li>


            </ul>
            <a href="/employee" class="btn btn-secondary  active" role="button" aria-pressed="true">Back</a>


        </div>
    </nav>

    <form:form action="/editcontract" modelAttribute="contractDto" cssStyle="margin-top: 15px" method="post">
        <div class="form-group row">
            <form:label path="phone" for="phone" cssClass="col-sm-2 col-form-label">Phone number</form:label>
            <div class="col-sm-10">
                <form:input path="phone" cssClass="form-control" id="phone" readonly="true"/>
            </div>
        </div>
        <div class="form-group row">
            <form:label path="clientDto.firstName" for="editFirstName" cssClass="col-sm-2 col-form-label">FirstName</form:label>
            <div class="col-sm-10">
                <form:input path="clientDto.firstName" cssClass="form-control" id="editFirstName" required="required"/>
            </div>
        </div>
        <div class="form-group row">
            <form:label path="clientDto.lastName" for="editLastName" cssClass="col-sm-2 col-form-label">LastName</form:label>
            <div class="col-sm-10">
                <form:input path="clientDto.lastName" cssClass="form-control" id="editLastName" required="required"/>
            </div>
        </div>
        <div class="form-group row">
            <form:label path="clientDto.address" for="editAddress" cssClass="col-sm-2 col-form-label">Address</form:label>
            <div class="col-sm-10">
                <form:input path="clientDto.address" cssClass="form-control" id="editAddress" required="required"/>
            </div>
        </div>
        <div class="form-group row">
            <form:label path="clientDto.birthday" for="editBirthday" cssClass="col-sm-2 col-form-label">Birthday</form:label>
            <div class="col-sm-10">
                <form:input path="clientDto.birthday" type="date"   cssClass="form-control" id="editBirthday" required="required" />
            </div>
        </div>
        <div class="form-group row">
            <form:label path="clientDto.email" for="editEmail" cssClass="col-sm-2 col-form-label">Email</form:label>
            <div class="col-sm-10">
                <form:input path="clientDto.email" type="email"  cssClass="form-control" id="editEmail" required="required" readonly="true"/>
            </div>
        </div>
        <div class="form-group row">
            <form:label path="clientDto.passport_series" for="editSeries" cssClass="col-sm-2 col-form-label">Passport series</form:label>
            <div class="col-sm-10">
                <form:input path="clientDto.passport_series"   cssClass="form-control" id="editSeries" required="required" readonly="true"/>
            </div>
        </div>
        <div class="form-group row">
            <form:label path="clientDto.passport_number" for="editNumber" cssClass="col-sm-2 col-form-label">Passport number</form:label>
            <div class="col-sm-10">
                <form:input path="clientDto.passport_number"  cssClass="form-control"  id="editNumber" required="required" readonly="true"/>
            </div>
        </div>
        <div class="form-group row">
            <form:label path="blockByOperator" for="editBlockByOperator" cssClass="col-sm-2 ">Operator block</form:label>
            <div class="col-sm-10 ">
                <form:checkbox path="blockByOperator"    id="editBlockByOperator"  />
            </div>
        </div>
        <div class="form-group row">
            <form:label path="blockByClient" for="editBlockByClient" cssClass="col-sm-2 ">Client block</form:label>
            <div class="col-sm-10">
                <form:checkbox path="blockByClient"    id="editBlockByClient" />
            </div>
        </div>

        <div class="form-group row">
            <form:label path="tariffDto" for="editTariff" cssClass="col-sm-2 col-form-label">Tariff</form:label>
            <div class="col-sm-10">
                <form:select path="tariffDto" class="form-control" id="selectmenu" required="required" onchange="toggle(this)" >
                    <c:forEach items="${listTariffs}" var="tariff">
                        <c:choose>
                            <c:when test="${contractDto.tariffDto.id==tariff.id}">

                                <form:option   value="${tariff.id}" selected="selected">Connected tariff ${tariff.tariff}</form:option>
                            </c:when>
                            <c:otherwise>

                                <form:option  value="${tariff.id}">${tariff.tariff}</form:option>
                            </c:otherwise>
                        </c:choose>

                    </c:forEach>
                </form:select>
            </div>
        </div>





        <c:forEach items="${listTariffs}" var="tar">
            <div id="${tar.id}" style="display: none">
                <div class="form-group row">
                    <label class="col-sm-2 col-form-label">Options</label>
                    <div class="col-sm-10">
                        <c:forEach items="${tar.options}" var="opt">
                            <div class="form-group row">

                                <div class="col-sm-10">
                                    <c:choose>
                                    <c:when test="${contractDto.tariffDto.id==tar.id && contractDto.connectedOptions.contains(opt)}">
                                    <input type="checkbox" name="${tar.id}" id="${opt.id}" value="${opt.id}" checked="checked" onchange="check(${opt.requirementsId},
                                        ${opt.exclusionsId}, ${tar.id}, this)"/>
                                    <label  for="${opt.id}">id:${opt.id}  Option: ${opt.name}</label>

                                    </c:when>
                                    <c:otherwise>
                                        <input type="checkbox" name="${tar.id}"  id="${opt.id}"  value="${opt.id}" onchange="check(${opt.requirementsId},
                                            ${opt.exclusionsId}, ${tar.id}, this)"/>
                                        <label   for="${opt.id}">id:${opt.id}  Option: ${opt.name}</label>
                                    </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </c:forEach>

                    </div>
                </div>
            </div>
        </c:forEach>




        <div class="form-group row justify-content-end">
            <div class="col-sm-10 ">
                <form:button type="submit" class="btn btn-secondary">Save</form:button>
            </div>
        </div>
    </form:form>

    <c:if test="${result!=null}">
        <div id="message" ><p style="margin-top: 10px; color: green" >${result}</p></div>
    </c:if>


    <div class="card-footer text-muted" style="margin-top: 20px">
        Created at night
    </div>
</div>

<script>
    var amountSlctOnExclOptions=0;
    var previoustariff =null;
    var selectmenu = document.getElementById("selectmenu");


   toggle(selectmenu);


    function toggle(sel) {
        console.log(sel);
        let id = sel.options[sel.selectedIndex].value;
        console.log("name = "+id);
        var tariff = document.getElementById(id);

        if (tariff.style.display == 'none') {
            tariff.style.display = 'block';
        }
        else {
            tariff.style.display = 'none';
        }
        if(previoustariff!=null){
            previoustariff.style.display = 'none';
        }
        previoustariff=tariff;

    }


    function onload() {
        console.log("onload amountSlctOnExclOptions "+amountSlctOnExclOptions)
        var sel = document.getElementById("selectmenu");
        var idl = Number(sel.options[sel.selectedIndex].value);

        var chekboxes = document.getElementsByName(idl);

        for(let checkbox of chekboxes){

            <c:forEach items="${optionsList}" var="option">
            if(checkbox.value==${option.id} && checkbox.checked==true){
                var requirementsIdOnLoad = ${option.requirementsId};
                var exclusionsIdOnLoad = ${option.exclusionsId};

                if ( requirementsIdOnLoad !=null && requirementsIdOnLoad.length>0){
                    for (let i = 0; i <= chekboxes.length - 1; i++){
                        if(requirementsIdOnLoad.includes(Number(chekboxes[i].value)) ){

                            chekboxes[i].onclick = function () {
                                return false;
                            }
                        }
                    }
                }
                if ( exclusionsIdOnLoad !=null && exclusionsIdOnLoad.length>0){
                    for (let i = 0; i <= chekboxes.length - 1; i++){
                        if(exclusionsIdOnLoad.includes(Number(chekboxes[i].value)) ){
                            amountSlctOnExclOptions++;
                            chekboxes[i].disabled=true;
                            console.log("onload amountSlctOnExclOptions after disabled "+amountSlctOnExclOptions)
                        }
                    }
                }


            }
            </c:forEach>
        }


    }

    window.onload=onload();



    /**
     * checks option what parameters have requirements before connecting
     *  instance x increase when another option have the same mutual exclusion option.
     *  when option will be disconnected and x !=0, this means that exclusion options should not be enabled.
     */


    function check(optRequirementsId, optExclusionsId, tariff, checkbox) {
        console.log("check amountSlctOnExclOptions "+amountSlctOnExclOptions)

        var elementsInTariffBlock = document.getElementsByName(tariff);
        if (checkbox.checked == true) {
            start:
                if (optExclusionsId.length > 0 || optRequirementsId.length>0){
                    for (let i = 0; i <= elementsInTariffBlock.length - 1; i++) {
                        if(optExclusionsId.includes(Number(elementsInTariffBlock[i].value)) && elementsInTariffBlock[i].checked==true){
                            alert("You cannot enable this option, because option with ID " +elementsInTariffBlock[i].value
                                + " is mutually exclusive with option ID "+checkbox.value);
                            checkbox.checked = false;
                            break start;
                        }
                        else if(optRequirementsId.includes(Number(elementsInTariffBlock[i].value)) && elementsInTariffBlock[i].checked==false){
                            alert("before enabling this option, all of the following option identifiers must be included: " + optRequirementsId);
                            checkbox.checked=false;
                            break start;
                        }
                    }
                    for (let i = 0; i <= elementsInTariffBlock.length - 1; i++) {
                        if(optExclusionsId.includes(Number(elementsInTariffBlock[i].value)) && elementsInTariffBlock[i].checked==false){
                            amountSlctOnExclOptions++;
                            elementsInTariffBlock[i].disabled=true;
                        }
                        else if(optRequirementsId.includes(Number(elementsInTariffBlock[i].value)) && elementsInTariffBlock[i].checked==true){
                            elementsInTariffBlock[i].onclick = function () {
                                return false;
                            }
                        }
                    }

                }

        }
        else {
            stop:
                if (optExclusionsId.length > 0 || optRequirementsId.length>0){
                    for (let i = 0; i <= elementsInTariffBlock.length - 1; i++) {
                        if(optRequirementsId.includes(Number(elementsInTariffBlock[i].value))){
                            elementsInTariffBlock[i].onclick = function () {
                                return true;
                            }
                        }
                        if(optExclusionsId.includes(Number(elementsInTariffBlock[i].value))){
                            if(--amountSlctOnExclOptions==0) elementsInTariffBlock[i].disabled=false;
                            console.log("check amountSlctOnExclOptions after disable "+amountSlctOnExclOptions)
                        }
                    }

                }
        }

    }

</script>


<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
        integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
        crossorigin="anonymous"></script>
</body>
</html>
