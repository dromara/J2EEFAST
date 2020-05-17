/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
//默认画笔大小 就是粗细
var NORMAL_STROKE = 2;
var SEQUENCEFLOW_STROKE = 1.5;
var ASSOCIATION_STROKE = 2;
var TASK_STROKE = 1;
var TASK_HIGHLIGHT_STROKE = 2;
var CALL_ACTIVITY_STROKE = 2;
var ENDEVENT_STROKE = 3;
//完成颜色
var COMPLETED_COLOR= "#017501";
var TEXT_COLOR= "#373e48";
//当前进行中颜色
var CURRENT_COLOR= "#FF0000";
var HOVER_COLOR= "#1890ff";
var ACTIVITY_STROKE_COLOR = "#bbbbbb";
var ACTIVITY_FILL_COLOR = "#f9f9f9";
//默认颜色
var MAIN_STROKE_COLOR = "#444";

var TEXT_PADDING = 3;
var ARROW_WIDTH = 4;
var MARKER_WIDTH = 12;

var TASK_FONT = {font: "11px Arial", opacity: 1, fill: Raphael.rgb(0, 0, 0)};

// icons
var ICON_SIZE = 16;
var ICON_PADDING = 4;
//面板初始宽度
var INITIAL_CANVAS_WIDTH;
//面板初始高度
var INITIAL_CANVAS_HEIGHT;

var paper;
var viewBox;
var viewBoxWidth;
var viewBoxHeight;

var canvasWidth;
var canvasHeight;

var modelDiv = jQuery('#bpmnModel');
var modelId = modelDiv.attr('data-model-id');
var historyModelId = modelDiv.attr('data-history-id');
var processDefinitionId = modelDiv.attr('data-process-definition-id');
//模型类型
var modelType = modelDiv.attr('data-model-type');
var isDebuggerEnabled = modelDiv.attr("data-debugger-enabled") == "true";
var isSelectEnabled = modelDiv.attr("data-select-enabled") == "true";
// Support for custom background colors for activities
var customActivityColors = modelDiv.attr('data-activity-color-mapping');
if (customActivityColors !== null && customActivityColors !== undefined && customActivityColors.length > 0) {
    // Stored on the attribute as a string
    customActivityColors = JSON.parse(customActivityColors);
}

var customActivityToolTips = modelDiv.attr('data-activity-tooltips');
if (customActivityToolTips !== null && customActivityToolTips !== undefined && customActivityToolTips.length > 0) {
    // Stored on the attribute as a string
    customActivityToolTips = JSON.parse(customActivityToolTips);
}

// Support for custom opacity for activity backgrounds
//设置透明度
var customActivityBackgroundOpacity = modelDiv.attr('data-activity-opacity');

var elementsAdded = new Array();
var elementsRemoved = new Array();

/**
 * 提示工具
 * j2eefast 修改
 */
function _showTip(htmlNode, element)
{

    // Custom tooltip
    var documentation = undefined;
    if (customActivityToolTips) {
        if (customActivityToolTips[element.name]) {
            documentation = customActivityToolTips[element.name];
        } else if (customActivityToolTips[element.id]) {
            documentation = customActivityToolTips[element.id];
        } else {
            documentation = ''; // Show nothing if custom tool tips are enabled
        }
    }

    // 默认提示工具, 也可以自定义
    if (documentation === undefined) {
        var documentation = "";
        //名称
        if (element.name && element.name.length > 0) {
            documentation += "<b>名称</b>: <i>" + element.name + "</i><br/><br/>";
        }

        if(modelType == 'runtime'){
            if(window.modelData){
                if(element.type == 'UserTask' && (element.completed || element.current)){
                    var id = element.id;
                    var data = window.modelData;
                    var flag = false;
                    for(var i=0; i< data.length; i++){
                        if(data[i].actId == id){
                            if(data[i].userId){
                                documentation += "<b>"+data[i].typeName+"人</b>: <i>" + data[i].userName + "</i><br/><br/><b>"+data[i].typeName+"时间</b>: "+data[i].endTime+"<br/><br/>";
                                flag = true;
                                break;
                            }
                        }
                    }
                    if(!flag){
                        documentation += "<b><span style='color: red; font-weight: bold'>无人处理</span><br/>";
                    }
                }
            }
        }else{
            //节点属性是否有值
            if (element.properties) {
                //name: "Assignee"
                // value: "${initiator}"
                for (var i = 0; i < element.properties.length; i++) {
                    var propName = element.properties[i].name;
                    var propValue = element.properties[i].value;
                    if(propName == "Assignee"){
                        propName = "处理人:"
                        continue;
                    }
                    if (element.properties[i].type && element.properties[i].type === 'list') {
                        documentation += '<b>' + propName + '</b>:<br/>';
                        for (var j = 0; j < element.properties[i].value.length; j++) {
                            documentation += '<i>' + element.properties[i].value[j] + '</i><br/>';
                        }
                    }
                    else {
                        documentation += '<b>' + propName + '</b>: <i>' + element.properties[i].value + '</i><br/>';
                    }
                }
            }
        }
    }

    //var text = element.type + " ";
    var text = "";
    if (element.name && element.name.length > 0)
    {
        text += element.id ;//+ " - " + element.type
    }
    else
    {
        //text += element.id;
        return;

    }

    htmlNode.qtip({
        content: {
            text: documentation,
            title: {
                text: text
            }
        },
        position: {
            my: 'top left',
            at: 'bottom center',
            viewport: jQuery('#bpmnModel')
        },
        hide: {
            fixed: true, delay: 500,
            event: 'click mouseleave'
        },
        style: {
            classes: 'ui-tooltip-kisbpm-bpmn'
        }
    });
}

function _addHoverLogic(element, type, defaultColor)
{
    var strokeColor = _bpmnGetColor(element, defaultColor);
    var topBodyRect = null;
    if (type === "rect")
    {
        topBodyRect = paper.rect(element.x, element.y, element.width, element.height);
    }
    else if (type === "circle")
    {
        var x = element.x + (element.width / 2);
        var y = element.y + (element.height / 2);
        topBodyRect = paper.circle(x, y, 15);
    }
    else if (type === "rhombus")
    {
        topBodyRect = paper.path("M" + element.x + " " + (element.y + (element.height / 2)) +
            "L" + (element.x + (element.width / 2)) + " " + (element.y + element.height) +
            "L" + (element.x + element.width) + " " + (element.y + (element.height / 2)) +
            "L" + (element.x + (element.width / 2)) + " " + element.y + "z"
        );
    }

    var opacity = 0;
    var fillColor = "#ffffff";
    if (jQuery.inArray(element.id, elementsAdded) >= 0)
    {
        opacity = 0.2;
        fillColor = "green";
    }

    if (jQuery.inArray(element.id, elementsRemoved) >= 0)
    {
        opacity = 0.2;
        fillColor = "red";
    }

    topBodyRect.attr({
        "opacity": opacity,
        "stroke" : "none",
        "fill" : fillColor
    });

    //设置提示
    _showTip(jQuery(topBodyRect.node), element);

    topBodyRect.mouseover(function() {
        paper.getById(element.id).attr({"stroke":HOVER_COLOR});
    });

    topBodyRect.mouseout(function() {
        paper.getById(element.id).attr({"stroke":strokeColor});
    });
}

function _zoom(zoomIn)
{
    var tmpCanvasWidth, tmpCanvasHeight;
    if (zoomIn)
    {
        tmpCanvasWidth = canvasWidth * (1.0/0.90);
        tmpCanvasHeight = canvasHeight * (1.0/0.90);
    }
    else
    {
        tmpCanvasWidth = canvasWidth * (1.0/1.10);
        tmpCanvasHeight = canvasHeight * (1.0/1.10);
    }

    if (tmpCanvasWidth != canvasWidth || tmpCanvasHeight != canvasHeight)
    {
        canvasWidth = tmpCanvasWidth;
        canvasHeight = tmpCanvasHeight;
        paper.setSize(canvasWidth, canvasHeight);
    }
}

var modelUrl;

if (modelType == 'runtime') {
	// if (historyModelId) {
    // 	modelUrl = FLOWABLE.APP_URL.getProcessInstanceModelJsonHistoryUrl(historyModelId);
	// } else {
    // 	modelUrl = FLOWABLE.APP_URL.getProcessInstanceModelJsonUrl(modelId);
	// }

    if (historyModelId && historyModelId != "") {
        modelUrl = baseURL  + "app/rest/process-instances/history/" + historyModelId + "/model-json"
    } else {
        if (modelId && modelId != "") {
            if (isDebuggerEnabled) {
                modelUrl = baseURL + "app/rest/process-instances/debugger/" + modelId + "/model-json"
            } else {
                modelUrl = baseURL + "app/rest/process-instances/" + modelId + "/model-json"
            }
        } else {
            if (processDefinitionId && processDefinitionId != "") {
                modelUrl = baseURL + "app/rest/process-definitions/" + processDefinitionId + "/model-json"
            }
        }
    }
} else if (modelType == 'design') {
	if (historyModelId) {
    	modelUrl = FLOWABLE.APP_URL.getModelHistoryModelJsonUrl(modelId, historyModelId);
	} else {
    	modelUrl = FLOWABLE.APP_URL.getModelModelJsonUrl(modelId);
	}
} else if (modelType == 'process-definition') {
    modelUrl = FLOWABLE.APP_URL.getProcessDefinitionModelJsonUrl(processDefinitionId);
}

function _showProcessDiagram() {

    if (modelUrl == undefined) {
        return
    }

    jQuery.ajax({
        type: "GET",
        //请求地址
        url: modelUrl + '?nocaching=' + new Date().getTime(),
        //请求成功
        success: function (data) {
            if ((!data.elements || data.elements.length == 0) && (!data.pools || data.pools.length == 0)) return;

            INITIAL_CANVAS_WIDTH = data.diagramWidth;

            if (modelType == 'design') {
                INITIAL_CANVAS_WIDTH += 20;
            } else {
                INITIAL_CANVAS_WIDTH += 30;
            }

            INITIAL_CANVAS_HEIGHT = data.diagramHeight + 50;
            canvasWidth = INITIAL_CANVAS_WIDTH;
            canvasHeight = INITIAL_CANVAS_HEIGHT;
            viewBoxWidth = INITIAL_CANVAS_WIDTH;
            viewBoxHeight = INITIAL_CANVAS_HEIGHT;

            if (modelType == 'design') {
                var headerBarHeight = 170;
                var offsetY = 0;
                if (jQuery(window).height() > (canvasHeight + headerBarHeight)) {
                    offsetY = (jQuery(window).height() - headerBarHeight - canvasHeight) / 2;
                }

                if (offsetY > 50) {
                    offsetY = 50;
                }

                jQuery('#bpmnModel').css('marginTop', offsetY);
            }

            // jQuery('#bpmnModel').width(INITIAL_CANVAS_WIDTH);
            //jQuery('#bpmnModel').height(INITIAL_CANVAS_HEIGHT);
            //创建画布
            paper = Raphael(document.getElementById('bpmnModel'), canvasWidth, canvasHeight);
            paper.setViewBox(0, 0, viewBoxWidth, viewBoxHeight, false);
            paper.renderfix();

            if (data.pools) {
                for (var i = 0; i < data.pools.length; i++) {
                    var pool = data.pools[i];
                    _drawPool(pool);
                }
            }

            var modelElements = data.elements;

            //画流程图 框
            for (var i = 0; i < modelElements.length; i++) {
                var element = modelElements[i];
                try {
                    console.log("--->>>" + "_draw" + element.type);
                    var drawFunction = eval("_draw" + element.type);
                    drawFunction(element);
                } catch (err) {
                    console.log(err);
                }
            }
            //画流程 线
            if (data.flows) {
                for (var i = 0; i < data.flows.length; i++) {
                    var flow = data.flows[i];
                    if (flow.type === 'sequenceFlow') {
                        _drawFlow(flow);
                    } else if (flow.type === 'association') {
                        _drawAssociation(flow);
                    }
                }
            }

            // var modelElements = data.elements;
            // for (var i = 0; i < modelElements.length; i++) {
            //     var element = modelElements[i];
            //     try {
            //         var drawFunction = eval("_draw" + element.type);
            //         drawFunction(element);
            //         if (isSelectEnabled && !startEventId && element.type == "StartEvent") {
            //             startEventId = element.id
            //         }
            //         if (isDebuggerEnabled) {
            //             _drawBreakpoint(element);
            //             if (element.brokenExecutions) {
            //                 for (var j = 0; j < element.brokenExecutions.length; j++) {
            //                     _drawContinueExecution(element.x + 25 + j * 10, element.y - 15, element.brokenExecutions[j], element.id)
            //                 }
            //             }
            //         }
            //     } catch (err) {
            //         log(err)
            //     }
            // }
        },
        //请求失败，包含具体的错误信息
        error: function (e) {
            console.log(e.status);
            console.log(e.responseText);
            opt.error("获取流程图信息错误!");
        }
    });
}

    // var request = jQuery.ajax({
    //     type: 'get',
    //     url: modelUrl + '?nocaching=' + new Date().getTime()
    // });
    //
    // request.success(function(data, textStatus, jqXHR) {
    //
    //     console.log("--->");
    //     if ((!data.elements || data.elements.length == 0) && (!data.pools || data.pools.length == 0)) return;
    //
    //     INITIAL_CANVAS_WIDTH = data.diagramWidth;
    //
    //     if (modelType == 'design') {
    //         INITIAL_CANVAS_WIDTH += 20;
    //     } else {
    //         INITIAL_CANVAS_WIDTH += 30;
    //     }
    //
    //     INITIAL_CANVAS_HEIGHT = data.diagramHeight + 50;
    //     canvasWidth = INITIAL_CANVAS_WIDTH;
    //     canvasHeight = INITIAL_CANVAS_HEIGHT;
    //     viewBoxWidth = INITIAL_CANVAS_WIDTH;
    //     viewBoxHeight = INITIAL_CANVAS_HEIGHT;
    //
    //     if (modelType == 'design') {
    //         var headerBarHeight = 170;
    //         var offsetY = 0;
    //         if (jQuery(window).height() > (canvasHeight + headerBarHeight))
    //         {
    //             offsetY = (jQuery(window).height() - headerBarHeight - canvasHeight) / 2;
    //         }
    //
    //         if (offsetY > 50) {
    //             offsetY = 50;
    //         }
    //
    //         jQuery('#bpmnModel').css('marginTop', offsetY);
    //     }
    //
    //     jQuery('#bpmnModel').width(INITIAL_CANVAS_WIDTH);
    //     jQuery('#bpmnModel').height(INITIAL_CANVAS_HEIGHT);
    //     paper = Raphael(document.getElementById('bpmnModel'), canvasWidth, canvasHeight);
    //     paper.setViewBox(0, 0, viewBoxWidth, viewBoxHeight, false);
    //     paper.renderfix();
    //
    //     if (data.pools)
    //     {
    //         for (var i = 0; i < data.pools.length; i++)
    //         {
    //             var pool = data.pools[i];
    //             _drawPool(pool);
    //         }
    //     }
    //
    //     var modelElements = data.elements;
    //     for (var i = 0; i < modelElements.length; i++)
    //     {
    //         var element = modelElements[i];
    //         //try {
    //         var drawFunction = eval("_draw" + element.type);
    //         drawFunction(element);
    //         //} catch(err) {console.log(err);}
    //     }
    //
    //     if (data.flows)
    //     {
    //         for (var i = 0; i < data.flows.length; i++)
    //         {
    //             var flow = data.flows[i];
    //             if (flow.type === 'sequenceFlow') {
    //                 _drawFlow(flow);
    //             } else if (flow.type === 'association') {
    //                 _drawAssociation(flow);
    //             }
    //         }
    //     }
    // });
    //
    // request.error(function(jqXHR, textStatus, errorThrown) {
    //     alert("error");
    // });
// }
//
    //$(function() {
  _showProcessDiagram()
   //});

