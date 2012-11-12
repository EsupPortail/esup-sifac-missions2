<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>

<c:url value="/resources" var="resources"/>
<link href="${ resources }/css/styles.css" rel="stylesheet"/>

<portlet:renderURL var="backURL"/>

<div class="portlet esup-sifacmiss2">
<div data-role="header" class="titlebar portlet-titlebar">
  <a href="${ backURL }" data-role="button" data-icon="back" data-inline="true">
    <spring:message code="mission.back"/>
  </a>
  <h2>${ mission.motif }</h2>
</div>
<div data-role="content" class="content portlet-content mission">
  <ul data-role="listview" data-inset="true">
    <li>
      <span class="ui-li-aside">${ mission.ordre }</span>
      <spring:message code="mission.label.ordre"/>
    </li>
    <c:choose>
      <c:when test="${ mission.periode.debut != mission.periode.fin }">
        <li>
          <span class="ui-li-aside">
            <fmt:formatDate value="${ mission.periode.debut }" type="date"/>
          </span>
          <spring:message code="mission.label.debut"/>
        </li>
        <li>
          <span class="ui-li-aside">
            <fmt:formatDate value="${ mission.periode.fin }" type="date"/>
          </span>
          <spring:message code="mission.label.fin"/>
        </li>
      </c:when>
      <c:otherwise>
        <li>
          <span class="ui-li-aside">
            <fmt:formatDate value="${ mission.periode.debut }" type="date"/>
          </span>
          <spring:message code="mission.label.date"/>
        </li>
      </c:otherwise>
    </c:choose>
    <li>
      <span class="ui-li-aside">
        <fmt:formatNumber value="${ mission.remboursement }" type="currency" currencyCode="EUR"/>
      </span>
      <spring:message code="mission.label.montant"/>
    </li>
    <li>
      <span class="ui-li-aside">
        <c:choose>
          <c:when test="${ mission.status == 1 }">
            <spring:message code="mission.status.nonremboursable"/>
          </c:when>
          <c:when test="${ mission.status == 2 }">
            <spring:message code="mission.status.validee"/>
          </c:when>
          <c:otherwise>
            <spring:message code="mission.status.attente"/>
          </c:otherwise>
        </c:choose>
      </span>
      <spring:message code="mission.label.status"/>
    </li>
    <li>
      <span class="ui-li-aside">
        <c:choose>
          <c:when test="${ not empty mission.date }">
            <fmt:formatDate value="${ mission.date }" type="date"/>
          </c:when>
          <c:otherwise>
            <spring:message code="mission.paiement.blank"/>
          </c:otherwise>
        </c:choose>
      </span>
      <spring:message code="mission.label.paiement"/>
    </li>
  </ul>
  <c:if test="${ not empty details }">
    <ul data-role="listview" data-inset="true" class="details">
      <li data-role="list-divider">
        <spring:message code="mission.label.details"/>
      </li>
      <c:forEach items="${ details }" var="detail">
        <li class="${ detail.paid ? 'paid' : '' }">
          <span class="ui-li-aside">
            <fmt:formatNumber value="${ detail.montant }" type="currency" currencyCode="EUR"/>
          </span>
          <span class="picto-${not empty detail.categorie ? detail.categorie : 'default'}">
            ${ detail.description }
          </span>
        </li>
      </c:forEach>
    </ul>
  </c:if>
</div>
</div>