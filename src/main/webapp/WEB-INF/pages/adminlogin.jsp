<html>
<body>
<link rel="icon" type="image/png" href="static/images/favicon.ico"/> <!-- This is for favicon of our page -->
User is  trying to login at
<br>
<br>
    <div id="time">

    </div>
    <br>
    <br>
    <div>
            <marquee>Made with Love by Prasanth!!</marquee>
    </div>

    <script type="text/javascript">

    function updateTime() {
            document.getElementById("time").innerText = new Date().toString();
    }

    //update for every second
    setInterval(updateTime, 1000)

    </script>

</body>
</html>