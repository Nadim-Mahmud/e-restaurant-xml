<%--
User: nadimmahmud
Date: 12/7/22
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en"/>
<fmt:setBundle basename="net.therap.estaurant" var="lang"/>
<nav class="navbar navbar-expand-lg bg-body-tertiary sticky-top bg-light px-4 mb-1 shadow-sm">
    <c:set var="admin" value="ADMIN" scope="page"/>
    <c:set var="chef" value="CHEF" scope="page"/>
    <c:set var="waiter" value="WAITER" scope="page"/>
    <c:set var="navChef" value="chef" scope="page"/>
    <c:set var="navWaiter" value="waiter" scope="page"/>
    <c:set var="category" value="category" scope="page"/>
    <c:set var="item" value="item" scope="page"/>
    <c:set var="resTable" value="resTable" scope="page"/>
    <c:set var="orders" value="orders" scope="page"/>
    <c:set var="orderForm" value="orderForm" scope="page"/>
    <c:set var="notification" value="notification" scope="page"/>
    <div class="container-fluid m-0">
        <a href="/" class="navbar-brand">
            <fmt:message key="app.title"/>
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText"
                aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarText">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <c:if test="${activeUser.type == admin}">
                    <li class="nav-item">
                        <a class="nav-link active underline-hover" aria-current="page" href="/admin/chef">
                                ${navItem == navChef ? '<b>Chef</b>' : 'Chef'}
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active underline-hover" aria-current="page" href="/admin/waiter">
                                ${navItem == navWaiter ? '<b>Waiter</b>' : 'Waiter'}
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active underline-hover" aria-current="page" href="/admin/category">
                                ${navItem == category ? '<b>Category</b>' : 'Category'}
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active underline-hover" aria-current="page" href="/admin/item">
                                ${navItem == item ? '<b>Item</b>' : 'Item'}
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active underline-hover" aria-current="page" href="/admin/res-table">
                                ${navItem == resTable ? '<b>Table</b>' : 'Table'}
                        </a>
                    </li>
                </c:if>
                <c:if test="${activeUser.type == waiter}">
                    <li class="nav-item">
                        <a class="nav-link active underline-hover" aria-current="page" href="/waiter/orders">
                                ${navItem == orders ? '<b>Orders</b>' : 'Orders'}
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active underline-hover" aria-current="page" href="/waiter/new-order">
                                ${navItem == orderForm ? '<b>Order form</b>' : 'New order'}
                        </a>
                    </li>
                    <a class="nav-link active underline-hover" aria-current="page" href="/waiter/notification">
                            ${navItem ==  notification? '<b>Notification</b>' : 'Notification'}
                    </a>
                </c:if>
                <c:if test="${activeUser.type == chef}">
                    <a class="nav-link active underline-hover" aria-current="page" href="/chef/notification">
                            ${navItem ==  notification? '<b>Notification</b>' : 'Notification'}
                    </a>
                </c:if>
            </ul>
            <span class="navbar-text">
                <c:if test="${activeUser != null}">
                    <div class="nav-item dropdown">
                        <p class="nav-link dropdown-toggle text-black m-0 underline-hover" role="button" data-bs-toggle="dropdown"
                           aria-expanded="false">${activeUser.firstName}&nbsp${activeUser.lastName}&nbsp;(${activeUser.type.label}) &nbsp;
                        </p>
                        <ul class="dropdown-menu">
                            <li>
                                <a class="dropdown-item" href="/update-profile">
                                    <fmt:message key="button.update.profile"/>
                                </a>
                            </li>
                            <li>
                                <a class="dropdown-item" href="/update-password">
                                    <fmt:message key="button.update.password"/>
                                </a>
                            </li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li>
                                <a class="m-0 dropdown-item" href="/logout">
                                    <fmt:message key="button.logout"/>
                                </a>
                            </li>
                        </ul>
                    </div>
                </c:if>
                <c:if test="${activeUser == null && loginPage == null}">
                    <a href="/login-page" type="button" class="btn btn-primary btn-sm px-4 text-white">
                        <fmt:message key="button.login"/>
                    </a>
                </c:if>
            </span>
        </div>
    </div>
</nav>