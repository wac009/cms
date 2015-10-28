
/*********************************************************************************************************/
/**
 * LrcShow (Music Player with Lyrics Rolling) Plugin For Fckeditor (Author: Lajox ; Email: lajox@19www.com)
 */
/*********************************************************************************************************/


FCKeditor LrcShow (Music Player with Lyrics Rolling) Plugin:

Add Description:

1. Upload lrcshow folder to  fckeditor/editor/plugins 

2. Modify Fckeditor/fckconfig.js Content:
    Increase the line of code:
	FCKConfig.Plugins.Add( 'lrcshow', 'zh-cn,en') ; //Music Player with Lyrics Rolling Plugin
    
3. Add a value 'LrcShow' to FCKConfig.ToolbarSets

----------------------------------------------------
Parameters description:

full html code:

<iframe border="0" 
src="/editor/fckeditor/editor/plugins/lrcshow/lrc.php?url=/attachment/media/1.mp3&player=wmp&lrc=/attachment/file/lrc.txt&auto=no&loop=no" 
width="400" height="295" frameborder="0" scrolling="no" scrollbar="no">
</iframe>


url:  music url (must not be empty!)
player: Player Type (value: wmp rmp; wmp: windows media player; rmp: real player)
auto: AutoStart (value: yes no)
loop: Repeat (value: yes no)


---------
e.g :
lrc.php?url=http://127.0.0.1/1.mp3&lrc=/attachment/file/lrc.txt
Please set file "/attachment/file/lrc.txt" to UTF-8 encoding 

lrc.txt Sample :

[ti:Love Me Not] 
[ar:t.A.T.u.] 
[al:Dangerous And Moving] 
[by:danfeng927] 
[00:00.00][01:35.20][02:01.19][02:51.93]www.51lrc.com  
[00:00.30][00:07.80]I complicated our lives 
[00:02.62]By falling in love with him 
[00:09.88]Now I’m losing my only friend 
[00:15.06]I don’t know why, I had to try 
[00:18.33]Living my life on the other side 
[00:21.53]Now I’m so confused 
[00:24.96][01:09.21][02:15.57]I don’t know what to do 
[00:29.44][00:36.83][01:13.70][01:21.04][01:43.32][01:50.59][02:20.21][02:27.57][02:34.76][02:42.16]He loves me, He loves me not 
[00:33.10][01:17.41][01:46.90][02:23.85][02:31.17][02:38.48]She loves me, She loves me not 
[00:40.42][01:54.21]She loves me... 
[00:44.73]I started blurring the lines 
[00:47.11]Because I didn’t care 
[00:51.98]I started crossing the line 
[00:54.45]Cause you were never there 
[00:59.35][02:05.79]No where to turn,No one to help, 
[01:02.60][02:09.06]It’s almost like I don’t even know myself 
[01:05.73][02:12.17]Now I have to choose 
[01:24.72]She loves me, she loves me 
[01:28.23][01:56.58][02:49.48]t.A.T.u.-Love Me Not 
[02:45.84]She loves me, She loves me not 


