<%--
  User: nadimmahmud
  Since: 1/19/23
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en"/>
<fmt:setBundle basename="net.therap.estaurant" var="lang"/>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>
        <fmt:message key="chef.order.accept.page.title"/>
    </title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<%@ include file="nvabar.jsp" %>
<div class="container">
    <div class="container col-md-5 mt-2">
        <div class="card">
            <div class="card-body">
                <h5 class="text-center mb-3">
                    <fmt:message key="chef.order.accept.form.title"/>
                </h5>
                <form:form action="/chef/notification/form/save" modelAttribute="orderLineItem" method="post">
                    <div class="mb-3">
                        <label for="estCookingTime" class="form-label">
                            <fmt:message key="label.est.timeToCook"/>
                        </label>
                        <form:input path="estCookingTime" class="form-control"/>
                        <form:errors path="estCookingTime" cssClass="text-danger"/>
                    </div>
                    <div class="d-grid gap-2">
                        <button type="submit" class="btn btn-success btn-sm">
                            <fmt:message key="button.accept"/>
                        </button>
                    </div>
                </form:form>
            </
            >
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous">
</script>
</body>
</html>