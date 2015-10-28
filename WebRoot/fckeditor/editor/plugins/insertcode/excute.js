
function runCode(obj){
	var winname = window.open("", "_blank", "");
         winname.document.open("text/html", "replace");
	winname.opener = null;
        //winname.document.write(document.getElementById(obj).value); 
         winname.document.write(obj.value); 
         winname.document.close();
		}
function copyCode(obj) {
	  var winname = document.body.createTextRange(); 
		  winname.moveToElementText(obj);
          winname.scrollIntoView(); 
          winname.select(); 
          winname.execCommand("Copy"); 
          winname.collapse(false);
          //alert("Copy Code Successfully!"); 
        }
function saveCode(obj) { 
         var winname = window.open("", "_blank", "top=10000"); 
         winname.document.open("text/html", "replace"); 
         winname.document.write(obj.value); 
         winname.document.execCommand("saveas","","code.html"); 
         winname.close(); 
        }

/*** ***/

function RunHTMLcode(obj){
winEx=window.open('', 'winEx', 'width=800,height=600,resizable=yes,top=0,left=0');
winEx.document.write(obj.value);
winEx.document.close();
}

function SelectHTMLcode(obj){
obj.select(); 
}

/**********************************************************************/
function $(id)
	{
 	return document.getElementById(id);
}

//复制文本
function copyIdText(id)
	{
  	copy( $(id).innerText,$(id) );
}
function copyIdHtml(id)
	{
  	copy( $(id).innerHTML,$(id) );
}

function copy(txt,obj)
	{       
   	if(window.clipboardData) 
   	{        
        window.clipboardData.clearData();        
        window.clipboardData.setData("Text", txt);
        alert("OK!") /*Copy Code Successfully!*/
        if(obj.style.display != 'none'){
   var rng = document.body.createTextRange();
   rng.moveToElementText(obj);
   rng.scrollIntoView();
   rng.select();
   rng.collapse(false);  
       }
   }
   else
    alert("Please Select The Text and Use The Key \"Ctrl+C\" To Copy"); /*请选中文本，使用 Ctrl+C 复制!*/
}