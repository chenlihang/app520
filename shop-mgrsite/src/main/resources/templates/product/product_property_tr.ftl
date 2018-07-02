<#list propertyList as property>
    <tr>
        <td>
            ${property.name}
            <input type="hidden"  name="productPropertyValueList[${property_index}].name" value="${property.name}">
        </td>
        <td>
            <#if property.type == 1>
                <select class="form-control" name="productPropertyValueList[${property_index}].value">
                    <#list property.propertyValueList as proval>
                        <option value="${proval.value}">${proval.value}</option>
                    </#list>
                </select>
            <#else>
                <input class="form-control" name="productPropertyValueList[].value">
            </#if>

        </td>
    </tr>
</#list>
