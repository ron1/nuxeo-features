<#setting url_escaping_charset='UTF-8'>

<@nxthemes_button identifier="canvas_editor"
  controlledBy="editor buttons"
  switchTo="editor perspectives/canvas editor"
  label="Refresh theme" />

<#if theme.saveable>
  <@nxthemes_button identifier="save"
    controlledBy="editor buttons"
    link="javascript:NXThemesEditor.saveTheme('${theme.src?js_string}', 2)"
    label="Save changes" />
</#if>

<@nxthemes_button identifier="theme_actions"
  controlledBy="editor buttons"
  classNames="dropList"
  menu="nxthemesThemeActions"
  label="More actions" />

<div id="nxthemesThemeActions" style="display: none"> 
  <ul class="nxthemesDropDownMenu">
    <li><a href="javascript:void(0)" onclick="NXThemesEditor.manageStyles()">Manage theme styles</a></li>
    <li><a href="javascript:void(0)" onclick="NXThemesEditor.managePresets()">Manage theme presets</a></li>
    <#if theme.exportable>
      <li><a href="javascript:void(0)" onclick="window.location='${basePath}/nxthemes-editor/xml_export?src=${theme.src?url}&amp;download=1&amp;indent=2'">Download theme</a></li>  
      <li><a href="javascript:void(0)" onclick="window.location='${basePath}/nxthemes-editor/xml_export?src=${theme.src?url}'">Show source XML</a></li>
    </#if>
    <#if theme.repairable><li><a href="javascript:void(0)" onclick="NXThemesEditor.repairTheme('${theme.src?js_string}')">Repair theme</a></li></#if>
    <#if theme.reloadable><li><a href="javascript:void(0)" onclick="NXThemesEditor.loadTheme('${theme.src?js_string}')">Restore theme from source</a></li></#if>
    <li><a href="javascript:void(0)" onclick="NXThemesEditor.deletePage('${current_page_path?js_string}')">Delete this page</a></li> 
    <#if theme.custom><li><a href="javascript:void(0)" onclick="NXThemesEditor.deleteTheme('${theme.src?js_string}')">Delete theme</a></li></#if>
  </ul>
</div>