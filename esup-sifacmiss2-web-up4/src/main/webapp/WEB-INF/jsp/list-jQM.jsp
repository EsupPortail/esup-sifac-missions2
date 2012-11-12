<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>

<c:url value="/resources" var="resources"/>
<link href="${ resources }/css/styles.css" rel="stylesheet"/>

<portlet:actionURL var="changeYearURL">
  <portlet:param name="action" value="changeYear"/>
</portlet:actionURL>

<div class="portlet esup-sifacmiss2">
<div data-role="content" class="content portlet-content">
  <div data-role="fieldcontain">
  <form:form commandName="yearSelectionForm" action="${ changeYearURL }">
    <form:select path="year">
      <form:options items="${ years }"/>
    </form:select>
  </form:form>
  </div>
  <c:if test="${ empty missions }">
    <p class="empty">
      <spring:message code="missions.empty"/>
    </p>
  </c:if>
  <ul data-role="listview" data-inset="true">
    <c:forEach items="${ missions }" var="mission">
      <portlet:renderURL var="missionURL">
        <portlet:param name="action" value="mission"/>
        <portlet:param name="id" value="${ mission.numero }"/>
      </portlet:renderURL>
      <li>
        <a href="${ missionURL }">
          ${ mission.motif }
          <span class="ui-li-count ui-btn-up-c ui-btn-corner-all">
            <fmt:formatNumber value="${ mission.remboursement }" type="currency" currencyCode="EUR"/>
          </span>
        </a>
      </li>
    </c:forEach>
  </ul>
</div>
</div>

<script type="text/javascript">
  var $ = jQuery || up.jQuery,
      $year = $('#year');
  
  $year.change(function() {
    console.log("ok");
    $year.closest('form').submit();
  });
  
</script>