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

    .btn{
        display: block;
        width: 100%;
        background-color: rgb(211, 97, 196);
        border: 0;
        outline: none;
        font-size: var(--s_head);
        padding: 0.125em 0;
        border-radius: 0 0 3em 3em;

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


        .sign-up_box{
            padding: .4em;
            max-width: 250px;
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


        .sign-up_box{
            max-width: 1000px;


        }

    }
    </style>

</head>

<body>

<div class="container">

	<div class="center">
		<div class="sign-up_box">
			<h1 class="heading">Twitter Sign Up</h1>
			<form action="#">
				<input type="text" id="signup-firstname" class="name" placeholder="First Name">
				<input type="text" id="signup-lastname" class="name" placeholder="Last Name">
				<input type="email" id="signup-email" name="Email Address" placeholder="Email Address">
				<input type="password" id="signup-password" name="Create a Password" placeholder="Create a Password">
				<input type="password" id="signup-re-password" placeholder="Re-enter Password">

				<p style="color:red; display:none" id="signup-error"></p> <!-- The error message will be filled down below-->
			</form>
		</div>
		<button type="submit" formaction="#" class="btn" id = "btn-signup">Sign Up</button>
	</div>

</div>
        <script>

        function validateSignupForm(){

            var firstname = $("#signup-firstname").val();
            var lastname = $("#signup-lastname").val();
            var email = $("#signup-email").val();
            var password = $("#signup-password").val();
            var rePassword = $("#signup-re-password").val();

            var error = "";

            if (!firstname){
            error += "First Name can't be Empty. ";
            }
            if (!lastname){
            error += "Last name can't be Empty. ";
            }
            if (!email){
            error += "email can't be Empty. ";
            }
            if (!password){
            error += "password can't be Empty. ";
            }
            if (!rePassword){
            error += "Re entered password can't be Empty. ";
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


        $("#btn-signup").click(function(){


            var firstname = $("#signup-firstname").val();
            var lastname = $("#signup-lastname").val();
            var email = $("#signup-email").val();
            var password = $("#signup-password").val();
            var rePassword = $("#signup-re-password").val();

           var isFormValid = validateSignupForm();
           if (isFormValid){
                $("#signup-error").hide();

                var user = {
                "firstName" : firstname,
                "lastName" : lastname,
                "email" : email,
                "password" : password
                };

                $.ajax({
                    type: "POST",
                    url: "/signup",
                    data: JSON.stringify(user),
                    success: function(response) {
                        if (!!response) {
                        alert(response.message);
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