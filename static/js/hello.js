var counter = 2;
setInterval(function(){counter++; document.title = counter + " notifications"; }, 1000); //This is how whatsapp shows notifications on the title page itself when we got the new notification
//setInterval(function(){document.title = new Date().toString(); }, 1000 );