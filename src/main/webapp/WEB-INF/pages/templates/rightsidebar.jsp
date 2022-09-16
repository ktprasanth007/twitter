<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="side sticky" id="sideR">
    <div id='sideRSearch'>
      <i class="fas fa-search"></i>
      <span class="searchBox" role="textbox" contenteditable></span>
    </div>
    <div id='trends'>
      <div id="trends-title">
        <span id='trendsForYou' class='font20bold'>Trends for you</span>
        <i class="fas fa-cog"></i>
      </div>
      <div class='individual-trend'>
        <div class="category-trend">
          <div class='trend-topic'>Politics Trending</div>
          <i class="fas fa-angle-right trends-seemore"></i>
        </div>
        <div class='trend-title'>This is a trend</div>
        <div class='trend-counter'>200k Tweets</div>
      </div>
      <div class='individual-trend'>
        <div class="category-trend">
          <div class='trend-topic'>Politics Trending</div>
          <i class="fas fa-angle-right trends-seemore"></i>
        </div>
        <div class='trend-title'>This is a trend</div>
        <div class='trend-counter'>200k Tweets</div>
      </div>
      <div id='trend-showMore'>Show more</div>
    </div>
    <div id='recommendations'>
      <div id="recommendations-title">
        <span id='Recommendations' class='font20bold'>Recommendations for you :)</span>
      </div>
      <c:forEach var="members" items="${RECOMMENDATIONS}">
        <div class='individual-follow'>
          <div class='follow-user-icon'>
            <i class="far fa-user-circle"></i>
          </div>
          <c:if test="${members.is_image_uploaded}">
            <img class='img' id="profile-image" src="${members.image}">
          </c:if>
          <c:if test="${not members.is_image_uploaded}">
            <img class='img' id="profile-image" src="/static/images/default.png">
          </c:if>
          <div class='follow-user-info'>
            <span class='follow-user-name font20bold'>${members.firstName}</span>
            <span class='follow-user-username'>${members.email}</span>
          </div>
          <c:if test="${not members.is_followed}">
            <button class='follow-member' member-id="${members.id}" type='submit' tabindex='0' style='margin-top: 12px;'>
              <span tabindex='-1'>Follow</span>
            </button>
          </c:if>
          <c:if test="${members.is_followed}">
            <button class='follow-member followed-button' member-id="${members.id}" type='submit' tabindex='0' style='margin-top: 12px;'>
              <span tabindex='-1'>Followed</span>
            </button>
          </c:if>
        </div>
      </c:forEach>
      <div id='follow-show-more'>Show more</div>
    </div>
</div>