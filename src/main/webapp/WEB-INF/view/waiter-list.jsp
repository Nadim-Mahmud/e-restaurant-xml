<%--
  User: nadimmahmud
  Since: 1/19/23
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en"/>
<fmt:setBundle basename="net.therap.estaurant" var="lang"/>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>
        <fmt:message key="waiter.list.page.title"/>
    </title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<jsp:include page="nvabar.jsp"/>
<div class="container">
    <div class="d-flex justify-content-end mt-3 mb-1">
        <form:form class="m-0" action="${pageContext.request.contextPath}/admin/waiter/form" method="get">
            <button class="btn btn-success btn-sm">
                <fmt:message key="button.add.waiter"/>
            </button>
        </form:form>
    </div>
    <div class="text-center">
        <c:if test="${success != null}">
            <p class="text-success">
                &check; ${success}!
            </p>
        </c:if>
        <c:if test="${failed != null}">
            <p class="text-danger">
                &cross; ${failed}
            </p>
        </c:if>
    </div>
    <table class="table table-hover table-sm align-middle text-center">
        <thead class="table-head bg-primary bg-opacity-50">
        <tr>
            <th scope="col" class="text-center">
                <fmt:message key="label.serial"/>
            </th>
            <th scope="col" class="text-center">
                <fmt:message key="label.firstName"/>
            </th>
            <th scope="col" class="text-center">
                <fmt:message key="label.lastName"/>
            </th>
            <th scope="col" class="text-center">
                <fmt:message key="label.dob.short"/>
            </th>
            <th scope="col" class="text-center">
                <fmt:message key="label.email"/>
            </th>
            <th scope="col" class="text-center">
                <fmt:message key="label.joiningDate"/>
            </th>
            <th scope="col" class="text-center">
                <fmt:message key="label.table"/>
            </th>
            <th scope="col" class="text-center">
                <fmt:message key="label.action"/>
            </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${waiterList}" var="waiter" varStatus="waiterStat">
            <tr>
                <td class="text-center">
                    <c:out value="${waiterStat.count}"/>
                </td>
                <td class="text-center">
                    <c:out value="${waiter.firstName}"/>
                </td>
                <td class="text-center">
                    <c:out value="${waiter.lastName}"/>
                </td>
                <td class="text-center">
                    <fmt:formatDate value="${waiter.dateOfBirth}" type="date"/>
                </td>
                <td class="text-center">
                    <c:out value="${waiter.email}"/>
                </td>
                <td class="text-center">
                    <fmt:formatDate value="${waiter.joiningDate}" type="date"/>
                </td>
                <td class="text-start">
                    <c:if test="${waiter.restaurantTableList.size() == 0}">x</c:if>
                    <ul>
                        <c:forEach items="${waiter.restaurantTableList}" var="resTable">
                            <li><c:out value="${resTable.name}"/></li>
                        </c:forEach>
                    </ul>
                </td>
                <td>
                    <div class="d-flex justify-content-center my-1">
                        <c:url var="updateUrl" value="${pageContext.request.contextPath}/admin/waiter/form">
                            <c:param name="waiterId" value="${waiter.id}"/>
                        </c:url>
                        <a class="text-center my-0 mx-2 p-0" href="${updateUrl}">
                            <button class="btn btn-outline-primary center btn-sm">
                                <fmt:message key="button.update"/>
                            </button>
                        </a>
                        <c:url var="deleteUrl" value="${pageContext.request.contextPath}/admin/waiter/delete">
                            <c:param name="waiterId" value="${waiter.id}"/>
                        </c:url>
                        <form:form class="text-center my-0 mx-2 p-0" action="${deleteUrl}" method="post">
                            <button class="btn btn-outline-danger center btn-sm"
                                    onclick="return confirm('Are you sure to delete?')">
                                <fmt:message key="button.delete"/>
                            </button>
                        </form:form>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN"
        crossorigin="anonymous">
</script>
</body>
</html>

