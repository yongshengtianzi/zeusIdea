function zeus() {};
zeus.valToTagId = function(tagName, tagVal, goalDoc) {//通过ID为标签赋值
	if (goalDoc.getElementById(tagName)) {
		goalDoc.getElementById(tagName).value = tagVal;
		return "1";
	} else {
		return "0";
	}
};
zeus.valToRadios = function(radName, tagVal, goalDoc) {//为单选按钮标签赋值
	var radios = goalDoc.getElementsByName(radName);
	if (radios) {
		for (var i = 0; i < radios.length; i++) {
			if (radios[i].value == tagVal) {
				radios[i].checked = true;
				break;
			}
		}
	}
};
zeus.valToCheckBox = function(cbName, tagVal, goalDoc) {//为多选按钮标签赋值
	var checkBoxs = goalDoc.getElementsByName(cbName);
	if (checkBoxs) {
		for (var j = 0; j < tagVal.length; j++) {
			for (var i = 0; i < checkBoxs.length; i++) {
				if (checkBoxs[i].value == tagVal[j]) {
					checkBoxs[i].checked = true;
					break;
				}
			}
		}
	}
};
zeus.valToSelect = function(selName, tagVal, goalDoc) {//为select下拉框标签赋值
	var selects = goalDoc.getElementsByName(selName);
	if (selects) {
		for (var i = 0; i < tagVal.length; i++) {
			selects[i].value = tagVal[i];
		}
	}
};
zeus.valToOther = function(othName, tagVal, goalDoc) {//为其他标签赋值
	var others = goalDoc.getElementsByName(othName);
	if (others) {
		for (var i = 0; i < tagVal.length; i++) {
			others[i].value = tagVal[i];
		}
	}
};
zeus.valToTagName = function(tagName, tagVal, goalDoc) {//通过name来为标签赋值
	var tag = goalDoc.getElementsByName(tagName);
	if (tag[0]) {
		if (tag[0].nodeName = "input") {
			var tagType = tag[0].type;
			if (tagType == "radio") {
				this.valToRadios(tagName, tagVal, goalDoc);
			} else if (tagType == "checkbox") {
				this.valToCheckBox(tagName, tagVal, goalDoc);
			} else {
				this.valToOther(tagName, tagVal, goalDoc);
			}
		} else if (tag[0].nodeName = "select") {
			this.valToSelect(tagName, tagVal, goalDoc);
		}
	}
};
zeus.valsToTags = function(jsonData, goalDoc) {//通过此方法将后台传过来的值赋给页面
	for (var dataFor in jsonData) {
		this.valToTagName(dataFor, jsonData[dataFor], goalDoc);
	}
};
zeus.selOpts = function(doc, id, array) {//为下拉框赋下拉选项
	var objId = doc.getElementById(id);
	for (var i = 0; i < array.length; i++) {
		var selOpts = new Option();
		selOpts.value = array[i].value;
		selOpts.text = array[i].text;
		objId.options.add(selOpts);
	}
};
zeus.radios = function(doc, id, name, array) {//为单选框赋值
	var objId = doc.getElementById(id);
	for (var i = 0; i < array.length; i++) {
		var label = doc.createElement("label");
		var rad = doc.createElement("input");
		rad.setAttribute("type", "radio");
		rad.setAttribute("name", name);
		rad.setAttribute("value", array[i].value);
		label.appendChild(rad);
		var textNode = document.createTextNode(array[i].text);
		label.appendChild(textNode);
		objId.appendChild(label);
		
	}
};
zeus.submit = function(obj) {//同步提交表单
	if (obj.startFunc) {
		if (obj.funParas) {
			if (obj.startFunc(obj.funParas) == false) return false;
		} else {
			if (obj.startFunc() == false) return false;
		}
	}
	if (typeof obj.formNum != "undefined") {
		obj.subDoc.forms[obj.formNum].action = obj.url;
		if (typeof obj.enctype != "undefined") obj.subDoc.forms[obj.formNum].enctype = obj.enctype;
		if (typeof obj.method != "undefined") obj.subDoc.forms[obj.formNum].method = obj.method;
		obj.subDoc.forms[obj.formNum].submit();
	} else {
		obj.subDoc.forms[0].action = obj.url;
		obj.subDoc.forms[0].submit();
	}
};
zeus.tckuangShow = function(goalDoc, tckSRC) {//弹出框窗口
	var divtck = goalDoc.getElementById("tc-kuang");
	var ifrtck = goalDoc.getElementById("tc-kuang-ifr");
	if (divtck) {
		if (ifrtck) {
			divtck.style.display = "block";
			ifrtck.src = tckSRC;
		} else {
			alert("弹出框设置异常！");
		}
	} else {
		alert("页面不存在弹出框设置！");
	}
};
zeus.ymtagClickFun = function(divId, tagId, val) {//日期控件点击事件
	document.getElementById(tagId).value = val;
	zeus.removeTag(divId);
	//document.body.removeChild(document.getElementById(divId));
};
zeus.nameTag = function(div, tags) {//组装标签
	var tagName = tags.tagName;//获取标签名
	if (!document.createElement(tagName)) {
		console.log("标签不存在");
		return;
	}
	var tagObj = document.createElement(tagName);
	if (tags.className) {//设置class值
		tagObj.setAttribute("class", tags.className);
	}
	if (tags.text) {//设置文本节点
		var textNode = document.createTextNode(tags.text);
		tagObj.appendChild(textNode);
	}
	if (tags.click) {//设置点击事件
		tagObj.setAttribute("onclick", tags.click);
	}
	if (tags.styles) {//设置style样式
		tagObj.setAttribute("style", tags.styles);
	}
	div.appendChild(tagObj);
	return tagObj;
};
zeus.dateVar;
zeus.ymtagLRClickFun = function(obj, lr) {//日期按钮中，左右切换月份
	var tempVal = new Date();
	if (obj.value != null && obj.value != "") {
		tempVal = new Date(Number(obj.value.substr(0, 4)), Number(obj.value.substr(5, 2)) - 1, Number(obj.value.substr(8)));
	}
	if (lr == "L") {
		tempVal = new Date(tempVal.getFullYear(), tempVal.getMonth() - 1, tempVal.getDate());
	}
	if (lr == "R") {
		tempVal = new Date(tempVal.getFullYear(), tempVal.getMonth() + 1, tempVal.getDate());
	}
	obj.value = tempVal.getFullYear() + "-" + (tempVal.getMonth() + 1 > 9 ? tempVal.getMonth() + 1 : "0" + (tempVal.getMonth() + 1)) + "-" + tempVal.getDate();
	zeus.date(obj);
};
zeus.dateToWeek = function(ymd) {//获取入参日期是年份的第几周
	var ny = Number(ymd.substr(0, 4));//获取入参的年份；
	var nm = Number(ymd.substr(5, 2)) - 1;//获取入参的月份；
	var nd = Number(ymd.substr(8));//获取入参的日；
	var date = new Date(ny, 0, 1);
	var wk = date.getDay();
	var fistweek = (7 - wk)%7;//1+fistweekleft号为第一周最后天，1+fistweekleft+1为第二周第一天
	date.setFullYear(ny, 0, fistweek + 2);
	var dymd = new Date(ny, nm, nd);
	wk = wk == 0 ? 6 : wk - 1;
	return parseInt((dymd.getTime() - date.getTime())/(1000 * 60 * 60 * 24 * 7)) + 1 + (wk > 3 ? 0 : 1);
};
zeus.removeTag = function(id) {
	if (document.getElementById(id)) {
		document.body.removeChild(document.getElementById(id));
	}
};
//获取控件左绝对位置

zeus.getAbsoluteLeft = function (o) {
	oLeft = o.offsetLeft
	while(o.offsetParent!=null) {
		oParent = o.offsetParent
		oLeft += oParent.offsetLeft
		o = oParent
	}
	return oLeft
}
//获取控件上绝对位置
zeus.getAbsoluteTop = function (o) {
	oTop = o.offsetTop;
	while(o.offsetParent!=null)
	{
		oParent = o.offsetParent
		oTop += oParent.offsetTop  // Add parent top position
		o = oParent
	}
	return oTop
}
zeus.date = function(self, czId) {//日期控件，tagId需要赋值的标签id，defaultVal默认值
	zeus.dateVar = self;
	zeus.removeTag("ym-datetag-id");
	var defaultVal = self.value;
	if (defaultVal == null || defaultVal == "") {
		var dateHere = new Date();
		defaultVal = dateHere.getFullYear() + "-" + (dateHere.getMonth() + 1 > 9 ? dateHere.getMonth() + 1 : "0" + (dateHere.getMonth() + 1)) + "-" + dateHere.getDate();
	}
	var year = Number(defaultVal.substr(0, 4));//获取当前年份
	var month = Number(defaultVal.substr(5, 2));//获取当前月份
	var dayNow = Number(defaultVal.substr(8));//获取当前日
	var ym = year + "-" + defaultVal.substr(5, 2) + "-";//获取年月，组装标签时用
	var tempDate = new Date(year, month - 1, 32);
	var monthDay = 32 - tempDate.getDate();//获取本月最后一天
	tempDate = new Date(year, month - 2, 32);
	var monthLastDay = 32 - tempDate.getDate();//获取上月最后一天
	tempDate = new Date(year, month - 1, 1);
	var monthWeeek = tempDate.getDay() == 0 ? 6 : tempDate.getDay() - 1;//获取本月1号是星期几
	var div = document.createElement("div");
	div.setAttribute("style", "top: " + (zeus.getAbsoluteTop(self) + 23) + "px;left: " + zeus.getAbsoluteLeft(self) + "px;");
	div.setAttribute("id", "ym-datetag-id");
	//组装年月
	var divNy = document.createElement("div");
	zeus.nameTag(divNy, {tagName: "span", className: "ymtag-gy-class ymtag-nyl-class", click: "zeus.ymtagLRClickFun(zeus.dateVar, 'L')"});
	zeus.nameTag(divNy, {tagName: "span", className: "ymtag-gy-class ymtag-ny-class", text: year + "年" + month + "月"});
	zeus.nameTag(divNy, {tagName: "span", className: "ymtag-gy-class ymtag-nyr-class", click: "zeus.ymtagLRClickFun(zeus.dateVar, 'R')"});
	div.appendChild(divNy);
	//组装周标题
	var divTil = document.createElement("div");
	zeus.nameTag(divTil, {tagName: "span", className: "ymtag-gy-class ymtag-zero-til-class"});
	zeus.nameTag(divTil, {tagName: "span", className: "ymtag-gy-class ymtag-one-til-class", text: "一"});
	zeus.nameTag(divTil, {tagName: "span", className: "ymtag-gy-class ymtag-two-til-class", text: "二"});
	zeus.nameTag(divTil, {tagName: "span", className: "ymtag-gy-class ymtag-three-til-class", text: "三"});
	zeus.nameTag(divTil, {tagName: "span", className: "ymtag-gy-class ymtag-four-til-class", text: "四"});
	zeus.nameTag(divTil, {tagName: "span", className: "ymtag-gy-class ymtag-five-til-class", text: "五"});
	zeus.nameTag(divTil, {tagName: "span", className: "ymtag-gy-class ymtag-six-til-class", text: "六"});
	zeus.nameTag(divTil, {tagName: "span", className: "ymtag-gy-class ymtag-seven-til-class", text: "日"});
	zeus.nameTag(divTil, {tagName: "span", className: "ymtag-gy-class ymtag-eight-til-class"});
	div.appendChild(divTil);
	var datas = [[], [], [], [], [], []];
	var start = 1 - monthWeeek;//循环开始次数
	//var end = 42 - monthWeeek;//循环结束次数
	var temp = 0;//临时变量
	for (var i = 0; i <= 5; i++) {
		if (i == 0 && monthWeeek > 3) {
			datas[i][0] = "-";
		} else if (monthDay - start < 3) {
			datas[i][0] = "-";
		} else {
			temp++;
			datas[i][0] = temp;
		}
		for (var j = 1; j <= 7; j++) {
			if (start <= 0) {
				datas[i][j] = monthLastDay + start;
			} else if (start > monthDay) {
				datas[i][j] = start - monthDay;
			} else {
				datas[i][j] = start;
			}
			start++;
		}
		if (i == 0 && monthWeeek > 3) {
			datas[i][8] = "-";
		} else if (i > 3 && datas[i][4] < 15) {
			datas[i][8] = "-";
		} else {
			datas[i][8] = zeus.dateToWeek(ym + datas[i][4]);
		}
	}
	for (var i = 0; i <= 5; i++) {
		var divTemp = document.createElement("div");
		divTemp.setAttribute("style", "height: 15px;");
		zeus.nameTag(divTemp, {tagName: "span", className: "ymtag-gy-class ymtag-zero-class", text: datas[i][0] + ""});
		if ((i == 0 && datas[i][1] > 7) || (i > 3 && datas[i][1] < 15)) {
			//判断该日是否属于当前月，不属于时处理
			zeus.nameTag(divTemp, {tagName: "span", className: "ymtag-gy-class ymtag-one-class", styles: "color: #A1A1A1;", text: datas[i][1] + ""});
		} else {
			var tempymd = ym + (datas[i][1] < 10 ? "0" + datas[i][1] : datas[i][1]);
			var tempStyle = "cursor: pointer;";
			if (dayNow == datas[i][1]) {
				tempStyle = tempStyle + "background-color: #CCCCCC;";
			}
			zeus.nameTag(divTemp, {tagName: "span", className: "ymtag-gy-class ymtag-one-class", styles: tempStyle, click: "zeus.ymtagClickFun(\"ym-datetag-id\", \"" + self.id + "\", \"" + tempymd + "\")", text: datas[i][1] + ""});
		}
		if ((i == 0 && datas[i][2] > 7) || (i > 3 && datas[i][2] < 15)) {
			//判断该日是否属于当前月，不属于时处理
			zeus.nameTag(divTemp, {tagName: "span", className: "ymtag-gy-class ymtag-two-class", styles: "color: #A1A1A1;", text: datas[i][2] + ""});
		} else {
			var tempymd = ym + (datas[i][2] < 10 ? "0" + datas[i][2] : datas[i][2]);
			var tempStyle = "cursor: pointer;";
			if (dayNow == datas[i][2]) {
				tempStyle = tempStyle + "background-color: #CCCCCC;";
			}
			zeus.nameTag(divTemp, {tagName: "span", className: "ymtag-gy-class ymtag-two-class", styles: tempStyle, click: "zeus.ymtagClickFun(\"ym-datetag-id\", \"" + self.id + "\", \"" + tempymd + "\")", text: datas[i][2] + ""});
		}
		if ((i == 0 && datas[i][3] > 7) || (i > 3 && datas[i][3] < 15)) {
			//判断该日是否属于当前月，不属于时处理
			zeus.nameTag(divTemp, {tagName: "span", className: "ymtag-gy-class ymtag-three-class", styles: "color: #A1A1A1;", text: datas[i][3] + ""});
		} else {
			var tempymd = ym + (datas[i][3] < 10 ? "0" + datas[i][3] : datas[i][3]);
			var tempStyle = "cursor: pointer;";
			if (dayNow == datas[i][3]) {
				tempStyle = tempStyle + "background-color: #CCCCCC;";
			}
			zeus.nameTag(divTemp, {tagName: "span", className: "ymtag-gy-class ymtag-three-class", styles: tempStyle, click: "zeus.ymtagClickFun(\"ym-datetag-id\", \"" + self.id + "\", \"" + tempymd + "\")", text: datas[i][3] + ""});
		}
		if ((i == 0 && datas[i][4] > 7) || (i > 3 && datas[i][4] < 15)) {
			//判断该日是否属于当前月，不属于时处理
			zeus.nameTag(divTemp, {tagName: "span", className: "ymtag-gy-class ymtag-four-class", styles: "color: #A1A1A1;", text: datas[i][4] + ""});
		} else {
			var tempymd = ym + (datas[i][4] < 10 ? "0" + datas[i][4] : datas[i][4]);
			var tempStyle = "cursor: pointer;";
			if (dayNow == datas[i][4]) {
				tempStyle = tempStyle + "background-color: #CCCCCC;";
			}
			zeus.nameTag(divTemp, {tagName: "span", className: "ymtag-gy-class ymtag-four-class", styles: tempStyle, click: "zeus.ymtagClickFun(\"ym-datetag-id\", \"" + self.id + "\", \"" + tempymd + "\")", text: datas[i][4] + ""});
		}
		if ((i == 0 && datas[i][5] > 7) || (i > 3 && datas[i][5] < 15)) {
			//判断该日是否属于当前月，不属于时处理
			zeus.nameTag(divTemp, {tagName: "span", className: "ymtag-gy-class ymtag-five-class", styles: "color: #A1A1A1;", text: datas[i][5] + ""});
		} else {
			var tempymd = ym + (datas[i][5] < 10 ? "0" + datas[i][5] : datas[i][5]);
			var tempStyle = "cursor: pointer;";
			if (dayNow == datas[i][5]) {
				tempStyle = tempStyle + "background-color: #CCCCCC;";
			}
			zeus.nameTag(divTemp, {tagName: "span", className: "ymtag-gy-class ymtag-five-class", styles: tempStyle, click: "zeus.ymtagClickFun(\"ym-datetag-id\", \"" + self.id + "\", \"" + tempymd + "\")", text: datas[i][5] + ""});
		}
		if ((i == 0 && datas[i][6] > 7) || (i > 3 && datas[i][6] < 15)) {
			//判断该日是否属于当前月，不属于时处理
			zeus.nameTag(divTemp, {tagName: "span", className: "ymtag-gy-class ymtag-six-class", styles: "color: #A1A1A1;", text: datas[i][6] + ""});
		} else {
			var tempymd = ym + (datas[i][6] < 10 ? "0" + datas[i][6] : datas[i][6]);
			var tempStyle = "cursor: pointer;";
			if (dayNow == datas[i][6]) {
				tempStyle = tempStyle + "background-color: #CCCCCC;";
			}
			zeus.nameTag(divTemp, {tagName: "span", className: "ymtag-gy-class ymtag-six-class", styles: tempStyle, click: "zeus.ymtagClickFun(\"ym-datetag-id\", \"" + self.id + "\", \"" + tempymd + "\")", text: datas[i][6] + ""});
		}
		if ((i == 0 && datas[i][7] > 7) || (i > 3 && datas[i][7] < 15)) {
			//判断该日是否属于当前月，不属于时处理
			zeus.nameTag(divTemp, {tagName: "span", className: "ymtag-gy-class ymtag-seven-class", styles: "color: #A1A1A1;", text: datas[i][7] + ""});
		} else {
			var tempymd = ym + (datas[i][7] < 10 ? "0" + datas[i][7] : datas[i][7]);
			var tempStyle = "cursor: pointer;";
			if (dayNow == datas[i][7]) {
				tempStyle = tempStyle + "background-color: #CCCCCC;";
			}
			zeus.nameTag(divTemp, {tagName: "span", className: "ymtag-gy-class ymtag-seven-class", styles: tempStyle, click: "zeus.ymtagClickFun(\"ym-datetag-id\", \"" + self.id + "\", \"" + tempymd + "\")", text: datas[i][7] + ""});
		}
		zeus.nameTag(divTemp, {tagName: "span", className: "ymtag-gy-class ymtag-eight-class", text: datas[i][8] + ""});
		div.appendChild(divTemp);
	}
	document.body.appendChild(div);
	event.cancelBubble = true;
	event.stopPropagation();
};
zeus.isNULL = function(doc, id) {//判断收入框是否为空，为空输出true；
	var idVal = doc.getElementById(id).value;
	if (idVal == null || idVal == "") {
		return true;
	}
	return false;
};
zeus.sessionStorage = {//获取sessionStorage
	getItem: function(key) {
		return sessionStorage.getItem(key);
	},
	setItem: function(key, value) {
		sessionStorage.setItem(key,value);
	},
	removeItem: function(key) {
		sessionStorage.removeItem(key);
	},
	getLength: function() {
		return sessionStorage.length;
	}
};
zeus.cache = {//缓存
		cache: {},
		write: function(name, val) {
				zeus.cache.cache[name] = val;
			},
		read: function(name) {
				return this.cache[name];
		}
};
zeus.gridAddData = function(id, isShow) {//为grid中添加数据
	var dataNum = zeus.cache.read("grid-" + id + "-formDataNow");
	if (dataNum == "" || dataNum == null) {
		dataNum = 0;
	}
	var url = zeus.cache.read("grid-" + id + "-url");
	if (/\?/.test(url)) {
		url+=("&data_num=" + dataNum);
	} else {
		url+=("?data_num=" + dataNum);
	}
	$.ajax({
		url : url,
		data : zeus.cache.read("grid-" + id + "-form"),
		type : 'post',
		cache : false,
		success : function(data) {
			if (data != null && data != "" && data != "0") {
				var datas = eval(data);
				dataNum+=datas.length;
				zeus.cache.write("grid-" + id + "-formDataNow", dataNum);
				zeus.showGridData(id, datas, false);
			} else {
				alert("grid下拉无数据！");
			}
		},
		error : function() {
			alert("grid下拉数据载入异常！");
		}
	});
};
zeus.jisuan = function(wh, tal) {
 	if (/%/.test(wh)) {
		return parseFloat("0." + wh.replace("%", "")) * tal
	}
 	return parseInt(wh);
};
zeus.gridScroll = function(id, isShow) {
	var div = document.getElementById(id).lastChild;
	var divF = document.getElementById(id).firstChild;
	divF.scrollLeft = div.scrollLeft
	var temp = true;
	if(isShow != "true") {
		temp = false;
	}
	if (div.scrollTop + div.clientHeight == div.scrollHeight) {
		zeus.gridAddData(id, temp);
	}
};
zeus.gridStyle = {width: ["20%", "30%", "20%", "20%", "20%"], head: ['headTh1 col1', 'headTh2 col2', 'headTh3 col3', 'headTh4 col4', 'headTh5 col5'], body: [['headTd1  col1', 'headTd2 col2', 'headTd3 col3', 'headTd4 col4', 'headTd5 col5'], ['headTd1 col1', 'headTd2 col2', 'headTd3 col3', 'headTd4 col4', 'headTd5 col5']]};
zeus.gridTilData = function(id, data) {//设置表格标题
	zeus.cache.write("grid-" + id + "-til", data);
};
zeus.showGridTil = function(id) {
	var style = zeus.gridStyle;
	var titles = zeus.cache.read("grid-" + id + "-til");
	var doc = zeus.cache.read("grid-" + id + "-doc");
	
	var tal = doc.getElementById(id).scrollWidth - 17;
	var widths = [];
	var width;
	
	var divHead = doc.createElement("div");
	divHead.setAttribute("class", "zeus-grid-tablehead-class");
	divHead.setAttribute("id", "grid-" + id + "-tablehead-id");
	divHead.setAttribute("style", "margin-right: 17px;background-color: #F0F0F0;overflow: hidden;");//设置标题div的右内边距17px，只在滚动型表格使用
	var table = doc.createElement("table");
	var tr = doc.createElement("tr");
	var thead = doc.createElement("thead");
	var numTemp = [];
	var div;
	for (var tilnum in titles) {
		var th = doc.createElement("th");
		div = doc.createElement("div");
		div.setAttribute("class", style.head[tilnum] + " grid-" + id + "-tablecolclass" + tilnum);
		div.setAttribute("num", tilnum);
		width = zeus.jisuan(style.width[tilnum], tal) - 1;
		widths[tilnum] = width;
		div.setAttribute("style", "width:" + width + "px;");
		numTemp[tilnum] = true;
		div.appendChild(doc.createTextNode(titles[tilnum]));
		th.appendChild(div);
		tr.appendChild(th);
	}
	zeus.cache.write("grid-" + id + "-tablewidths", widths);
	zeus.cache.write("grid-" + id + "-tablecolshow", numTemp);//设置当前表格展示属性
	thead.appendChild(tr);
	table.appendChild(thead);
	divHead.appendChild(table);
	doc.getElementById(id).appendChild(divHead);
};
zeus.showGridData = function(id, dats, flag) {
	var style = zeus.gridStyle;
	var datas = dats;
	var doc = zeus.cache.read("grid-" + id + "-doc");
	
	var divBody = doc.getElementById("zeus-grid-" + id);
	var table = doc.createElement("table");
	if(!divBody) {
		divBody = doc.createElement("div");
		divBody.setAttribute("id", "zeus-grid-" + id);
		divBody.setAttribute("class", "zeus-grid-tablebody-class");
		divBody.setAttribute("onscroll", "zeus.gridScroll('" + id + "', 'true')");
		var temp = doc.getElementById("grid-" + id + "-tablehead-id");
		var tempMain = doc.getElementById(id);
		
		divBody.setAttribute("style", "overflow-y: scroll;height:" + (tempMain.clientHeight - temp.clientHeight) + "px;");
		divBody.appendChild(table);
		doc.getElementById(id).appendChild(divBody);
	} else {
		var tagTemp = divBody.firstChild;
		if(flag) {
			if(tagTemp) {
				divBody.removeChild(tagTemp);
			}
			divBody.appendChild(table);
		} else {
			if(tagTemp) {
				table = tagTemp;
			} else {
				divBody.appendChild(table);
			}
		}
	}
	var showCol = zeus.cache.read("grid-" + id + "-tablecolshow");
	var bStyleN = 1;
	if (style.body.length == 2 && style.body[0] instanceof Array) {
		bStyleN = 2;
	}
	var div;
	var widths = zeus.cache.read("grid-" + id + "-tablewidths");
	for (datasnum in datas) {
		tr = doc.createElement("tr");
		for (datanum in datas[datasnum]) {
			var td = doc.createElement("td");
			div = doc.createElement("div");
			div.setAttribute("class", style.body[datasnum%bStyleN][datanum]);
			div.setAttribute("style", "width:" + widths[datanum] + "px;");
			if(!showCol[datanum]) {
				td.setAttribute("style", "display: none;");
			}
			div.appendChild(doc.createTextNode(datas[datasnum][datanum]));
			td.appendChild(div);
			tr.appendChild(td);
		}
		table.appendChild(tr);
	}
};
/*
 * zeus表格
 * doc文档的document
 * id文档中div的id
 * url获取数据的地址
 * formId获取条件的form
 * isShow是否初始化展示数据
 * 注：表格会通过url给后端返回当前表格中显示了多少条数据参数为data_num
 */
zeus.zeusGrid = function(doc, id, url, formId, isShow) {
	var titles = ['第一列', '第二列', '第三列', '第四列', '第五列'];
	if($(formId)) {
		zeus.cache.write("grid-" + id + "-form", $(formId).serialize());
	}
	zeus.cache.write("grid-" + id + "-url", url);
	zeus.cache.write("grid-" + id + "-doc", doc);
	zeus.gridTilData(id, titles);
	zeus.showGridTil(id);
	zeus.gridAddData(id, isShow);
};