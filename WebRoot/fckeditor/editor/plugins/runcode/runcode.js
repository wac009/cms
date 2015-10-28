function runCode(obj){
		 var winname = window.open("", "_blank", "");
         winname.document.open("text/html", "replace");
		 winname.opener = null;
         winname.document.write(document.getElementById(obj).value);
         winname.document.close();
		}
function copyCode(obj) {
		  var winname = document.body.createTextRange(); 
          winname.moveToElementText(document.getElementById(obj));
          winname.scrollIntoView(); 
          winname.select(); 
          winname.execCommand("Copy"); 
          winname.collapse(false);
          //alert("Copy Code Successfully!"); 
        }
function saveCode(obj) { 
         var winname = window.open("", "_blank", "top=10000"); 
         winname.document.open("text/html", "replace"); 
         winname.document.write(document.getElementById(obj).value); 
         winname.document.execCommand("saveas","","code.html"); 
         winname.close(); 
        }