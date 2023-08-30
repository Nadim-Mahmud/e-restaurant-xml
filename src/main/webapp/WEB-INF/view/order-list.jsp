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
        <fmt:message key="order.list.page.title"/>
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
    <c:set var="served" value="SERVED"/>
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
                <fmt:message key="label.table"/>
            </th>
            <th scope="col" class="text-center">
                <fmt:message key="label.ordered.at"/>
            </th>
            <th scope="col" class="text-center">
                <fmt:message key="label.itemList"/>
            </th>
            <th scope="col" class="text-center">
                <fmt:message key="label.bill"/>
            </th>
            <th scope="col" class="text-center">
                <fmt:message key="label.action"/>
            </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orderList}" var="order" varStatus="orderStat">
            <tr>
                <td class="text-center">
                    <c:out value="${orderStat.count}"/>
                </td>
                <td class="text-center">
                    <c:out value="${order.restaurantTable.name}"/>
                </td>
                <td class="text-center">
                    <fmt:formatDate value="${order.createdAt}" type="time"/>
                </td>
                <td class="text-start">
                    <ul>
                        <c:forEach items="${order.orderLineItemList}" var="orderLineItem">
                            <li><c:out value="${orderLineItem.item.name} x ${orderLineItem.quantity}"/></li>
                        </c:forEach>
                    </ul>
                </td>
                <td class="text-center">
                    <c:out value="${order.totalBill()} /-"/>
                </td>
                <td>
                    <div class="d-flex justify-content-center my-1">
                        <c:if test="${order.status != served }">
                            <c:url var="updateUrl" value="${pageContext.request.contextPath}/waiter/new-order">
                                <c:param name="orderId" value="${order.id}"/>
                            </c:url>
                            <a class="text-center my-0 mx-2 p-0" href="${updateUrl}">
                                <button class="btn btn-outline-primary center btn-sm">
                                    <fmt:message key="button.update"/>
                                </button>
                            </a>
                        </c:if>
                        <c:url var="cancelUrl" value="${pageContext.request.contextPath}/waiter/order/cancel">
                            <c:param name="orderId" value="${order.id}"/>
                        </c:url>
                        <form:form class="text-center my-0 mx-2 p-0" action="${cancelUrl}" method="post">
                            <button class="btn btn-outline-danger center btn-sm"
                                    onclick="return confirm('Are you sure to cancel the order?')">
                                <fmt:message key="button.cancel"/>
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

