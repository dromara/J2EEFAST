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
/*
 * j2eefast 扩展流程部署功能
 */
FLOWABLE.TOOLBAR.ACTIONS.deployProcess = function(process) {
    if (process) {
        var url = FLOWABLE.CONFIG.contextRoot + '/bpm/model/deployIndex?id=' + process.id,
            title = '部署流程模型', width = 700, height = 300;
        if (parent && parent.opt){
            parent.opt.layer.open({
                type: 2,
                shade: 0.6,
                shadeClose: true,
                resize: false,
                scrollbar: true,
                title: '<i class="glyphicon glyphicon-send"></i> '+parent.$.i18n.prop(title), area: [width+'px',height+'px'],
                content: url
            });
            // parent.opt.modal.open('<i class="glyphicon glyphicon-send"></i> '+parent.$.i18n.prop(title),
            //     url,width,height);
        }else{
            var top=parseInt((window.screen.height-height)/2,10),left=parseInt((window.screen.width-width)/2,10),
                options="location=no,menubar=no,toolbar=no,dependent=yes,minimizable=no,modal=yes,alwaysRaised=yes,"+
                    "resizable=yes,scrollbars=yes,"+"width="+width+",height="+height+",top="+top+",left="+left;
            window.open(url, title, options);
        }
    }
}