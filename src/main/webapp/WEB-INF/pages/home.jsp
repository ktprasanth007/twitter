<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
  <link rel="icon" type="image/png" href="/static/images/favicon.ico" />
  <!-- This is for favicon of our page -->
  <h3>
    <br>
    <br> &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; &emsp; Welcome ${MEMBER.firstName}
  </h3>

  <head>
    <script src="https://code.jquery.com/jquery-3.5.0.min.js" integrity="sha256-xNzN2a4ltkB44Mc/Jz3pT4iU1cmeR0FkXs4pru/JxaQ=" crossorigin="anonymous"></script>
    <link rel="stylesheet" type="text/css" href="/static/css/home.css" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.7.7/handlebars.min.js"></script>

  </head>

  <body>

    <style>
    #content {
        border-bottom: double;
    }
    #tweets-load-more {
      align-self: center;
      box-sizing: border-box;
      padding: 15px;
      color: #1da1f2;
    }
    #tweets-load-more:hover {
      background-color: rgba(66, 66, 66, 0.2);
      cursor: pointer;
    }
    </style>

    <div id="main">

      <jsp:include page="templates/leftsidebar.jsp" />

      <div id="content">
        <div id="homeDiv" class='sticky'>
          <span id="contentHome" class='font20bold'>Home</span>
        </div>
        <div id='whatsHappening'>
          <div id='whatsHapIcon'>
            <i class="far fa-user-circle"></i>
          </div>
          <div id='tweetEditor'>
            <span id='tweet-message' class="textarea" role="textbox" contenteditable></span>
            <div id='tweetIcons'>
              <div id='tweetIconsLeft'>
                <i class="far fa-image hoverDarkBackground"></i>
                <i class="fab fa-git-square hoverDarkBackground"></i>
                <i class="far fa-chart-bar hoverDarkBackground"></i>
                <i class="far fa-smile hoverDarkBackground"></i>
              </div>
              <div id='tweetIconsRight'>
                <button id="publishTweet" type="submit" tabindex="0">
                  <span tabindex="-1">Tweet</span>
                </button>
              </div>
            </div>
          </div>
        </div>

        <div id= "tweet-content">

        </div>
        <div id='tweets-load-more'>Load more</div>

        <script id="tweet-template" type="text/x-handlebars-template">
            {{#data}}
                <div class='individualTweet'>
                  <div class='tweetUserIcon'>
                    <i class="far fa-user-circle"></i>
                  </div>
                  <div class='individualContent'>
                    <div class='names'>
                      <img class='img' src="/static/images/default.png">
                      <div class='individualName'>{{author_name}}</div>
                      <div class="individualUsername">{{email}}</div>
                      <div class='dot'></div>
                      <div class="time">15s</div>
                    </div>
                    <div class="individualText">{{message}}</div>
                    <div class='individualIcons'>
                      <div class='individualInfo iconComment'>
                        <i class="far fa-comment"></i>
                        <span class='individualNumber' id='comments'>100</span>
                      </div>
                      <div class='individualInfo iconRetweet'>
                        <i class="fas fa-retweet"></i>
                        <span class='individualNumber' id='retweets'>100</span>
                      </div>
                      <div class='individualInfo iconLike'>
                        <i class="far fa-heart"></i>
                        </i>
                        <span class='individualNumber' id='likes'>100</span>
                      </div>
                      <div class='individualInfo iconShare'>
                        <i class="far fa-share-square"></i>
                      </div>
                    </div>
                  </div>
                </div>
            {{/data}}
        </script>
      </div>

            <jsp:include page="templates/rightsidebar.jsp" />
    </div>
    <script type="text/javascript">

        var tweet_ui_source = $("#tweet-template").html();
        var tweet_template = Handlebars.compile(tweet_ui_source);
        window.lastSeenTweet = 999999;

        function showTweets() {
            $.ajax({
                type: "POST",
                url: "/user/public-tweet/"+window.lastSeenTweet,
                success: function(response) {
                  if (!!response) {
                  var tweet_data = {
                                data: response <!-- We used to write name and tweet_text here as a list of values,here should not be any ; after response -->
                                <!-- This is used for only single line comments -->
                                };
                  }
                  var size = response.length; <!-- We should not put curly braces at the end this is not a java function -->
                  window.lastSeenTweet = response[size-1].id; <!-- Lets say we got 5 items in response and we want the last tweet Id so we gonna fetch the length of response and assign that l-1.id as lastseenTweet -->

                  $("#tweet-content").append(tweet_template(tweet_data));
                  <!-- Usually this is used but in our case when we click on load more we want to append these - $("#tweet-content").html(tweet_template(tweet_data)); -->
                },
                contentType: 'application/json'
              });
        }
      showTweets();

      $("#tweets-load-more").click(function() {
                showTweets();
         });

      function validateMessage() {
        var message = document.getElementById("tweet-message").innerText;
        var error = "";
        if (!message) {
          error += "message can't be Empty. ";
        }
        if (error.length > 0) {
          return false;
        }
        return true;
      }
      $("#publishTweet").click(function() {
        var isMessageValid = validateMessage();
        if (isMessageValid) {
          $.ajax({
            type: "POST",
            url: "/user/tweet",
            data: document.getElementById("tweet-message").innerText,
            success: function(response) {
              if (!!response) {
                window.location.reload();
              }
            },
            contentType: 'application/json'
          });
        } else {
          alert("Message can't be empty");
        }
      });
    </script>
    <script type="text/javascript" , src="/static/js/following-functions.js"></script>
    <!-- This is for follow and unfollow functions -->
  </body>
</html>