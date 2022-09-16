<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<h3>
 <br>
 <br>
 &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; Welcome ${MEMBER.firstName} </h3>

<head>

      <script src="https://code.jquery.com/jquery-3.5.0.min.js" integrity="sha256-xNzN2a4ltkB44Mc/Jz3pT4iU1cmeR0FkXs4pru/JxaQ=" crossorigin="anonymous"></script>

<style>
/*! General settings */
* {
  margin: 0;
  padding: 0;
  font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
  color: white;
}

body {
  background-color: black;
}

#main {
  /* border: 1px white solid; */
  width: 80%;
  margin: auto;
  display: flex;
  justify-content: center;
}

/*! Main content */
#content {
  width: 45%;
  display: flex;
  flex-direction: column;
  border: 0.2px #7c7c7c solid;
  border-top: none;
  border-bottom: none;
  height: 100%;
  border-bottom: none;
}

#homeDiv {
  box-sizing: border-box;
  padding: 15px;
  height: 50px;
  border: 0.2px #7c7c7c solid;
  border-top: 1px solid;
  background-color: black;
  /*Black*/
}

#follow-show-more {
  box-sizing: border-box;
  padding: 15px;
  color: #1da1f2;
}

#follow-show-more:hover {
  background-color: rgba(66, 66, 66, 0.2);
  cursor: pointer;
}


#recommendations {
  display: flex;
  flex-direction: column;
  background-color: rgba(73, 99, 102, 0.233);
  border-radius: 15px;
  padding: 0;
}

#recommendations-title,
.individual-follow {
  font-size: 10px;
  box-sizing: border-box;
  padding: 10px;
  border-bottom: 1px #7c7c7c solid;
}

.individual-follow {
  display: flex;
  align-items: center;
}

.follow-user-info {
  display: flex;
  align-items: left;
  flex-direction: column;
  box-sizing: border-box;
  padding: 5px;
  width: 70%;
}

.follow-user-icon i {
  font-size: 50px;
  margin-right: 10px;
}

.follow-member {
  color: #1da1f2;
  background-color: #1da1f2;
  border: 2px #1da1f2 solid;
  box-sizing: border-box;
  padding: 10px;
  font-weight: bold;
  border-radius: 50px;
}

.follow-member:hover {
  cursor: pointer;
}

/*? Reusable Classes */
.font20bold {
  font-size: 20px;
  font-weight: bold;
}

.sticky {
  position: sticky;
  position: -webkit-sticky;
  top: 0;
}

.img {
     border-radius: 100%;
     width: 40px;
     height: 40px;
     padding-right: 10px;
 }

.followed-button {
        background-color:#018786;
}

</style>

</head>

<body>
<div id="main">

  <div id="content">
    <div id='recommendations'>
      <div id="recommendations-title">
        <span id='Recommendations' class='font20bold'>People You have Followed :)</span>
          </div>
              <c:forEach var="members" items="${FOLLOWED}">
                  <div class='individual-follow'>
                          <div class='follow-user-icon'><i class="far fa-user-circle"></i></div>
                          <div class='follow-user-info'>
                            <span class='follow-user-name font20bold'>${members.firstName}</span>
                            <span class='follow-user-username'>${members.email}</span>
                          </div>
                          <button class='follow-member followed-button' member-id = "${members.id}" type = 'submit' tabindex= '0' style= 'margin-top: 12px;'>
                          <span tabindex='-1'>Followed</span>
                          </button>
                        </div>
              </c:forEach>
          <div id='follow-show-more'>Show more</div>
    </div>
  </div>
</div>

<script type="text/javascript", src="/static/js/following-functions.js"></script> <!-- This is for follow and unfollow functions -->

</body>

</html>
