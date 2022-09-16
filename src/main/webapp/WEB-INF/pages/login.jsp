<html>
<head>

<link rel="icon" type="image/png" href="static/images/favicon.ico"/> <!-- This is for favicon of our page -->

    <script
      src="https://code.jquery.com/jquery-3.6.1.min.js"
      integrity="sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ="
      crossorigin="anonymous">
    </script>

    <style>
    /* Font Family/Style -- Google Fonts */

    @import url('https://fonts.googleapis.com/css2?family=Kanit:wght@300;500&family=Roboto:wght@400;500&display=swap');
    *{
        padding: 0;
        margin: 0;
        box-sizing: border-box;
        font-family: 'Kanit', sans-serif;
    }
    :root{

        /* fonts size */
        --head: 3rem;
        --s_head: 1.6rem;
        --text: 1.125rem;
    }

    .container{
        min-height: 100vh;
        background: #26a7de;
        background: -webkit-linear-gradient(to right, #282189, #5144da);
        background: linear-gradient(to right, #282189, #5144da);
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .center{
        margin: 1em;

    }

    .sign-up_box{
        border: 0;
        border-radius: 2em 0 0 0;
        background-color: rgb(38 29 101 / 72%);
        max-width: 500px;
        text-align: center;
        padding: .8em .6em 1.3em .6em;

    }

    .heading{
        color: rgb(255, 255, 255);
        font-size: var(--head);
        margin-bottom: .3em;
    }


    input{
        padding: 0.5em 0.3em 0.5em 0.8em;
        width: 85%;
        margin: 0.4em 0;
        outline: none;
        border: 0;
        border-radius: 5px;
        font-size: var(--text);
    }

    input:focus{
        outline: none;
    }

    input::placeholder{
        text-align: center;
        color: rgb(66, 65, 65)
    }

    .name{
        max-width: 42% !important;
    }

    .btn {
              display: inline-block;
              margin: 1rem 0;
              padding: 0.75rem;
              cursor: pointer;
              outline: none;
              border: none;
              border-radius: 0.25em;
              font-size: 1rem;
              letter-spacing: 1.5px;
              font-weight: 500;
              transition: all 100ms ease-in-out;
              background-color: rgb(225,225,255);
         }

    .btn:hover{
        background-color: #f7eaf3;
        cursor: pointer;
    }


    /* For small mobile device */

    @media (min-width:100px) and (max-width:250px){
        :root{
            --head: 1.5rem;
            --s_head: 1rem;
            --text: .8rem;
        }


        .login_box{
            padding: .4em;
            max-width: auto;
        }

        .name{
            max-width: 100% !important;

        }

    }


    /* For mobile device  */

    @media (min-width:251px) and (max-width: 500px){


        .name{
            max-width: 100% !important;

        }
    }



    /* For 4k and full HD+ screen */

    @media (min-width:2000px){
        :root{

            /* fonts size */

            --head: 8rem;
            --s_head: 3.6rem;
            --text: 2.25rem;
        }


        .login_box{
            max-width: auto;

        }

    }
    </style>

</head>

<body>

<div class="container">

	<div class="center">
		<div class="login_box">
			<h1 class="heading">Welcome to Login Page</h1>
			<form action="#">
				<input type="email" id="signup-email" name="Email Address" placeholder="Email Address">
				<input type="password" id="signup-password" name="Enter the Password" placeholder="Enter the Password">
				<p style="color:red; display:none" id="signup-error"></p> <!-- The error message will be filled down below-->
			</form>
		</div>
		<button type="submit" formaction="#" class="btn" id = "btn-login">Login</button>
        <p>Don't have an account? <a href="/signup">Register</a>.</p>
	</div>

</div>
        <script>
        function validateSignupForm(){

                    var email = $("#signup-email").val();
                    var password = $("#signup-password").val();

                    var error = "";

                    if (!email){
                    error += "email can't be Empty. ";
                    }
                    if (!password){
                    error += "password can't be Empty. ";
                    }
                    if (!!password && password.length<6){
                        error += "Password should at least contain 6 characters. ";
                    }

                    if (error.length>0){
                        $("#signup-error").html(error);
                        return false;
                    }
                    return true;
                }

                $("#btn-login").click(function(){

                    var email = $("#signup-email").val();
                    var password = $("#signup-password").val();

                   var isFormValid = validateSignupForm();
                   if (isFormValid){
                        $("#signup-error").hide();

                        var user = {
                        "email" : email,
                        "password" : password
                        };

                        $.ajax({
                            type: "POST",
                            url: "/login",
                            data: JSON.stringify(user),
                            success: function(response) {
                                if (!!response) {
                                        if (response.is_logged_in === true) {
                                                location.href="/login/home"; <!-- This line directs to another API call which will return home page through home.jsp -->
                                        } else {
                                            $("#signup-error").html(response.message);
                                            $("#signup-error").show();
                                        }
                                }
                            },
                            contentType: 'application/json'
                        });

                   <!-- alert("The button was clicked "); -->
                   } else {
                        $("#signup-error").show();
                        alert("This form is invalid ");
                   }
                });
        </script>

</body>
</html>