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

<div class="fl-widget portlet esup-sifacmiss2">
<div class="fl-widget-titlebar titlebar portlet-titlebar">
  <div class="toolbar">
    <form:form commandName="yearSelectionForm" action="${ changeYearURL }">
      <form:label path="year">
        <spring:message code="missions.year.select"/>
      </form:label>
      <form:select path="year">
        <form:options items="${ years }"/>
      </form:select>
      <button>
         <spring:message code="missions.year.submit"/>
      </button>
    </form:form>
  </div>
</div>
<div class="fl-widget-content content portlet-content mission-list">
  <table class="portlet-table">
    <thead>
      <tr>
        <th>
          <spring:message code="missions.col.motif"/>
        </th>
        <th>
          <spring:message code="missions.col.remboursement"/>
        </th>
        <th>
          <spring:message code="missions.col.paiement"/>
        </th>
        <th>
          <spring:message code="missions.col.ordre"/>
        </th>
        <th>
          <spring:message code="missions.col.periode"/>
        </th>
      </tr>
    </thead>
    <tbody>
      <c:if test="${ empty missions }">
        <tr class="empty">
          <td colspan="5">
            <spring:message code="missions.empty"/>
          </td>
        </tr>
      </c:if>
      <c:forEach items="${ missions }" var="mission">
        <portlet:resourceURL id="details" var="detailsURL">
          <portlet:param name="id" value="${ mission.numero }"/>
        </portlet:resourceURL>
        <tr class="mission" data-id="${ mission.numero }" data-url="${ detailsURL }">
          <td>${ mission.motif }</td>
          <td>
            <fmt:formatNumber value="${ mission.remboursement }" type="currency" currencyCode="EUR"/>
          </td>
          <td>
            <c:choose>
              <c:when test="${ not empty mission.date }">
                <fmt:formatDate value="${ mission.date }" type="date"/>
              </c:when>
              <c:when test="${ mission.remboursement > 0 }">
                <spring:message code="missions.row.attente"/>
              </c:when>
            </c:choose>
          </td>
          <td>${ mission.ordre }</td>
          <td>
            <c:choose>
              <c:when test="${ mission.periode.debut != mission.periode.fin }">
                <fmt:formatDate value="${ mission.periode.debut }" var="from" type="date" dateStyle="full"/>
                <fmt:formatDate value="${ mission.periode.fin }" var="to" type="date" dateStyle="full"/>
                <spring:message code="missions.row.periode" arguments="${ from },${ to }"/>
              </c:when>
              <c:otherwise>
                <fmt:formatDate value="${ mission.periode.debut }" var="date" type="date" dateStyle="full"/>
                <spring:message code="missions.row.date" arguments="${ date }"/>
              </c:otherwise>
            </c:choose>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</div>
</div>

<script type="text/javascript">
  var $ = jQuery || up.jQuery,
      $portlet = $('.esup-sifacmiss2'),
      $table = $portlet.find('table tbody'),
      detailsCache = {};
  
  $table.find('tr').click(function() {
    var $this = $(this),
        id = $this.data('id');
    
    if ($this.hasClass('loading')) {
      return false;
    }
    
    if (!$this.hasClass('open')) {
      var buildDetails = function(id, data) {
        $this.after(data);
        $this.addClass('open');
      };
      
      if (!detailsCache[id]) {
        $this.addClass('loading');
        $.get($this.data('url')).success(function(data) {
          detailsCache[id] = data;
          buildDetails(id, data);
        }).complete(function() {
          $this.removeClass('loading');
        });
      } else {
        buildDetails(id, detailsCache[id]);
      }
    } else {
      $this.removeClass('open');
      $table.find('tr.details').each(function() {
        var $tr = $(this);
        if ($tr.data('mission') === id) {
          $tr.remove();
        }
      });
    }
  });
</script>