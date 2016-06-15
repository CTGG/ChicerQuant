canvasGraph=function(b){var a=document.getElementById(b);this.ctx=a.getContext("2d");this.ctx.clearRect(0,0,400,400);this.containerWidth=400;this.containerHeight=400;this.padding=10;this.xMid=this.containerWidth/2;this.yMid=this.containerHeight/2;this.startX=this.xMid-60;this.startY=this.yMid+60;this.gray1="#c1c1c1";this.gray2="#f1f1f1	";this.gray3="#787878";this.stepX=this.xMid/10;this.xMin=0;this.xMax=1000;this.yMin=-500;this.yMax=4000;this.zMin=0;this.zMax=1000;this.factor=(this.stepX/1.5);this.perspectiveFactor=1.2;this.drawAxis();this.drawInfo()};canvasGraph.prototype.drawAxis=function(){this.ctx.fillStyle=this.gray1;this.ctx.strokeStyle=this.gray1;this.ctx.beginPath();this.ctx.moveTo((this.startX),(this.startY));this.ctx.lineTo(this.padding,this.containerHeight-this.padding);this.ctx.stroke();this.ctx.closePath();this.ctx.fillRect(this.startX,this.padding,1,(this.startY-this.padding));this.ctx.fillRect(this.startX,this.startY,(this.startY-this.padding),1);this.yHeight=this.startY-(2*this.stepX);sx=this.startX;markH=this.containerHeight/100;sy=this.startY;this.ctx.strokeStyle=this.ctx.fillStyle=this.gray3;xx=sx;yy=sy;var a=0;this.marginX=this.padding+this.startY-(10*this.stepX)-(this.padding);for(i=0;i<10;i++){sx=sx+this.stepX;sy=sy-(this.stepX);xx=xx-this.factor;yy=yy+this.factor;a=i*this.factor*this.perspectiveFactor+(this.perspectiveFactor*this.factor-i);this.ctx.strokeStyle=this.ctx.fillStyle="rgba(200,200,200,0.5)";this.ctx.fillRect(sx,this.marginX,1,this.startY-this.marginX);this.ctx.fillRect(this.startX,sy,this.startY-this.marginX,1);this.ctx.fillRect(xx,yy,this.startY-this.marginX+a,1);this.ctx.beginPath();this.ctx.moveTo(sx,this.startY);this.ctx.lineTo(sx-((10*this.factor)-a),this.startY+((10)*this.factor));this.ctx.stroke();this.ctx.fillRect(xx,yy,1,(-1*this.yHeight-a));this.ctx.beginPath();this.ctx.moveTo(this.startX,sy);this.ctx.lineTo(this.startX-(10*this.factor),sy+(10*this.factor)-a);this.ctx.stroke();this.ctx.strokeStyle=this.ctx.fillStyle=this.gray3;this.ctx.fillRect(sx,this.startY-(markH/2),1,markH);this.ctx.fillRect(this.startX-(markH/2),sy,markH,1);this.ctx.beginPath();this.ctx.moveTo(xx-(markH/2),yy-(markH/2));this.ctx.lineTo(xx,yy);this.ctx.stroke()}};function drawMarkers(){this.ctx.textAlign="right";this.ctx.fillStyle="#000";this.ctx.textAlign="center";for(var a=0;a<3;a++){this.ctx.fillText("a",x_3d,0,10)}this.ctx.save()}canvasGraph.prototype.drawBar=function(a,e,d){x_min=this.xMin;x_max=this.xMax;y_min=this.yMin;y_max=this.yMax;z_min=this.zMin;z_max=this.zMax;graph_step_x=(x_max-x_min)/10;graph_step_y=(y_max-y_min)/10;graph_step_z=(z_max-z_min)/10;var c=((e/y_max*10)*this.factor*this.perspectiveFactor-(e/y_max*(10/this.perspectiveFactor))*this.perspectiveFactor)*(d/z_max*1);var b=((a/x_max*10)*this.factor*this.perspectiveFactor-(a/x_max*(10/this.perspectiveFactor))*this.perspectiveFactor)*(d/z_max*1);y_height_scaled=(e*this.stepX/graph_step_y)+c;x_width_scaled=(a*this.stepX/graph_step_x)+b;z_len_scaled=(d*this.factor/graph_step_z);x_scaled=this.startX+x_width_scaled;y_scaled=this.startY-y_height_scaled;x_3d=x_scaled-z_len_scaled;y_3d=y_scaled+z_len_scaled;this.ctx.fillStyle=this.ctx.strokeStyle="rgba(255,255,255,1)";this.ctx.beginPath();this.ctx.moveTo(x_3d-3,y_3d);this.ctx.lineTo(x_3d+3,y_3d);this.ctx.lineTo(x_3d+7,(y_3d-3));this.ctx.lineTo(x_3d,(y_3d-3));this.ctx.lineTo(x_3d-2,y_3d);this.ctx.closePath();this.ctx.fill();this.ctx.fillStyle="rgba(189,189,243,0.7)";this.ctx.fillRect(x_3d-3,y_3d,7,y_height_scaled);this.ctx.fillStyle="rgba(77,77,180,0.7)";this.ctx.fillRect(x_3d+4,y_3d-0,1,y_height_scaled);this.ctx.fillRect(x_3d+5,y_3d-1,1,y_height_scaled);this.ctx.fillRect(x_3d+6,y_3d-2,1,y_height_scaled);this.ctx.fillRect(x_3d+7,y_3d-3,1,y_height_scaled);this.ctx.fillStyle="rgba(0,0,0,0.7)";this.ctx.fillRect(x_3d-3,y_3d,1,y_height_scaled);this.ctx.fillRect(x_3d+7,y_3d-3,1,y_height_scaled);this.ctx.fillRect(x_3d-2,(y_3d+y_height_scaled),7,1);this.ctx.fillRect(x_3d-3,(y_3d-1),1,1);this.ctx.fillRect(x_3d-2,(y_3d-2),1,1);this.ctx.fillRect(x_3d-1,(y_3d-3),1,1);this.ctx.fillRect(x_3d+5,(y_3d-1+y_height_scaled),1,1);this.ctx.fillRect(x_3d+6,(y_3d-2+y_height_scaled),1,1);this.ctx.fillRect(x_3d+7,(y_3d-3+y_height_scaled),1,1);this.ctx.fillRect(x_3d,(y_3d-3),7,1)};canvasGraph.prototype.drawGraph=function(a){for(i=0;i<a.length;i++){this.drawBar(a[i].x,a[i].y,a[i].z)}};canvasGraph.prototype.drawInfo=function(){this.infoElm=document.getElementById("gInfo");this.infoElm.innerHTML='<div id="y-label"><font color="white">盈利情况:4000元分割线</font></div>';this.infoElm.innerHTML+='<div id="x-label"><font color="white">黄金交叉</font></div>';this.infoElm.innerHTML+='<div id="z-label"><font color="white">死亡交叉</font></div>';this.infoElm.innerHTML+='<div id="t-001" class="gText"><font color="white">kdj</font></div>';this.infoElm.innerHTML+='<div id="t-002" class="gText"><font color="white">macd</font></div>';this.infoElm.innerHTML+='<div id="t-003" class="gText"><font color="white">rsi</font></div>';this.infoElm.innerHTML+='<div id="t-004" class="gText"><font color="white">bias</font></div>';this.infoElm.innerHTML+='<div id="t-005" class="gText"><font color="white">kdj</font></div>';this.infoElm.innerHTML+='<div id="t-006" class="gText"><font color="white">macd</font></div>';this.infoElm.innerHTML+='<div id="t-007" class="gText"><font color="white">rsi</font></div>';this.infoElm.innerHTML+='<div id="t-008" class="gText"><font color="white">bias</font></div>';this.infoElm=document.getElementById("t-001").style.top="270px";this.infoElm=document.getElementById("t-001").style.left="100px";this.infoElm=document.getElementById("t-002").style.top="300px";this.infoElm=document.getElementById("t-002").style.left="65px";this.infoElm=document.getElementById("t-003").style.top="320px";this.infoElm=document.getElementById("t-003").style.left="40px";this.infoElm=document.getElementById("t-004").style.top="340px";this.infoElm=document.getElementById("t-004").style.left="20px";this.infoElm=document.getElementById("t-005").style.top="240px";this.infoElm=document.getElementById("t-005").style.left="170px";this.infoElm=document.getElementById("t-006").style.top="240px";this.infoElm=document.getElementById("t-006").style.left="210px";this.infoElm=document.getElementById("t-007").style.top="240px";this.infoElm=document.getElementById("t-007").style.left="270px";this.infoElm=document.getElementById("t-008").style.top="240px";this.infoElm=document.getElementById("t-008").style.left="310px"};function checkRange(c,b,a){if(c>=b&&c<=a){return true}else{alert("Invalid value: "+c)}}function sortNumByZ(e,d){var c=e.z;var f=d.z;return((c<f)?-1:((c>f)?1:0))};