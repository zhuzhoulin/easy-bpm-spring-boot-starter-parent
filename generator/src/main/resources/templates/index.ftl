<template>
    <div class="app-container">
        <div>
            <div class="filter-container" v-if="showSearch">
                <el-form :inline="true" :model="listQuery">
                <#list table.fields as field>
                    <#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['queryFlag'] == 1 >
                 <el-form-item label="<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>">
                     <#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formType'] == 'textarea' >
                         <el-input type="textarea" v-model="listQuery.${field.propertyName}" autosize  placeholder="请输入<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>" ></el-input>
                     <#elseif field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formType'] == 'select ' >
                      <el-select v-model="listQuery.${field.propertyName}" placeholder="请选择">
                          <el-option key="深圳" label="选项1" value="深圳"> </el-option>
                          <el-option key="上海" label="选项2" value="上海"> </el-option>
                      </el-select>
                     <#elseif field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formType'] == 'radio' >
                          <el-radio-group v-model="listQuery.${field.propertyName}" >
                              <el-radio :label="3">备选项</el-radio>
                              <el-radio :label="6">备选项</el-radio>
                              <el-radio :label="9">备选项</el-radio>
                          </el-radio-group>
                     <#elseif field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formType'] == 'switch' >
                         <el-switch v-model="listQuery.${field.propertyName}"></el-switch>
                     <#elseif field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formType'] == 'switch(0,1)' >
                         <el-switch v-model="listQuery.${field.propertyName}"  :active-value="1" :inactive-value="0"></el-switch>
                     <#elseif field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formType'] == 'checkbox' >
                        <el-checkbox-group v-model="listQuery.${field.propertyName}">
                            <el-checkbox label="复选框 A"></el-checkbox>
                            <el-checkbox label="复选框 B"></el-checkbox>
                            <el-checkbox label="复选框 C"></el-checkbox>
                        </el-checkbox-group>
                     <#else >
                         <#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['queryMethod'] == 'BETWEEN'>
                             <#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formType'] == 'date' >
                         <el-date-picker v-model="listQuery.${field.propertyName}Start"  type="date" placeholder="请输入开始<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>"></el-date-picker>
                           -
                         <el-date-picker v-model="listQuery.${field.propertyName}End"  type="date" placeholder="请输入结束<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>"></el-date-picker>
                             <#elseif field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formType'] == 'time' >
                         <el-time-select v-model="listQuery.${field.propertyName}Start"  type="date" placeholder="请输入开始<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>"></el-time-select>
                            -
                         <el-time-select v-model="listQuery.${field.propertyName}End"  type="date" placeholder="请输入结束<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>"></el-time-select>
                             <#elseif field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formType'] == 'datetime' >
                         <el-date-picker v-model="listQuery.${field.propertyName}Start"  type="datetime" placeholder="请输入开始<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>"></el-date-picker>
                            -
                         <el-date-picker v-model="listQuery.${field.propertyName}End"  type="datetime" placeholder="请输入结束<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>"></el-date-picker>
                             <#else >
                         <el-input v-model="listQuery.${field.propertyName}Start"  placeholder="请输入开始<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>" ></el-input>
                           -
                         <el-input v-model="listQuery.${field.propertyName}End"  placeholder="请输入结束<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>" ></el-input>
                             </#if>
                         <#else>
                             <#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formType'] == 'date' >
                         <el-date-picker v-model="listQuery.${field.propertyName}"  type="date" placeholder="请输入<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>"></el-date-picker>
                             <#elseif field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formType'] == 'time' >
                         <el-time-select v-model="listQuery.${field.propertyName}"  type="date" placeholder="请输入<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>"></el-time-select>
                             <#elseif field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formType'] == 'datetime' >
                         <el-date-picker v-model="listQuery.${field.propertyName}"  type="datetime" placeholder="请输入<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>"></el-date-picker>
                             <#else >
                         <el-input v-model="listQuery.${field.propertyName}"  placeholder="请输入<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>" ></el-input>
                             </#if>
                         </#if>
                     </#if>
                 </el-form-item>
                    </#if>
                </#list>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button type="success" @click="getList" icon="el-icon-search" v-waves>查询</el-button>
										<el-button @click="download" icon="el-icon-download" v-waves>导出</el-button>
                    <el-button type="primary" @click="handleCreate" icon="el-icon-plus" v-waves>添加</el-button>
                 </span>
            </div>
            <div class="table-container">
                <el-row :gutter="10" class="mb8">
                    <right-toolbar :showSearch.sync="showSearch" :columns="columns" @queryTable="getList" :defaultHideColumns.sync="defaultHideColumns"></right-toolbar>
                </el-row>
                <el-table v-loading="listLoading" :data="list" size="mini" element-loading-text="Loading" fit border highlight-current-row>
                    <el-table-column align="center" label="序号" width="95" sortable>
                        <template slot-scope="scope">
                            {{ (listQuery.pageIndex - 1 ) * listQuery.pageSize + scope.$index + 1 }}
                        </template>
                    </el-table-column>
                    <#list table.fields as field>
                        <#if field.propertyType == "Date" || "LocalDateTime" == field.propertyType>
                    <el-table-column label="${field.comment}" align="center" v-if="columns[${field_index}].visible" >
                        <template slot-scope="scope">
                            <span>{{scope.row.${field.propertyName}}}</span>
                        </template>
                    </el-table-column>
                        <#elseif field.propertyName == 'validState'>
                        <el-table-column label="状态" prop="${field.propertyName}" align="center" v-if="columns[${field_index}].visible">
                            <template slot-scope="scope">
                                <el-tag :type="scope.row.${field.propertyName} | statusFilter">
                                    <span>{{ statusMap[scope.row.${field.propertyName}]}}</span>
                                </el-tag>
                            </template>
                        </el-table-column>
                        <#else>
                    <el-table-column label="<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>" prop="${field.propertyName}" align="center" v-if="columns[${field_index}].visible" sortable :show-overflow-tooltip="true" ></el-table-column>
                        </#if>
                    </#list>
                    <el-table-column align="center" label="操作" fixed="right" min-width="180px">
                        <template slot-scope="scope">
                            <el-button size="mini" type="primary" @click="handleUpdate(scope.row)" icon="el-icon-edit" plain v-waves>编辑</el-button>
                            <el-button size="mini" type="danger" @click="handleDelete(scope.row)" icon="el-icon-delete" plain v-waves>禁用</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <!-- 分页 -->
                <div class="page-footer">
                    <pagination v-show="total>0" :total="total" :page.sync="listQuery.pageIndex" :limit.sync="listQuery.pageSize" style="float:right;" @pagination="getList" />
                </div>
            </div>

            <el-dialog :title="titleMap[dialogStatus]" :visible.sync="dialogVisible" width="40%" @close="handleDialogClose">
                <el-form ref="dataForm" :model="form" :rules="rules" label-width="100px" class="demo-ruleForm">
                    <#list table.fields as field>
                    <#--<#if field.propertyName != 'operatorId' && field.propertyName != 'operatorName' && field.propertyName != 'createTime' && field.propertyName != 'updateTime'>-->
                        <el-form-item v-if="<#if field.customMap['customFieldInfo']['insertFlag']?? && field.customMap['customFieldInfo']['insertFlag'] == 1 >true<#else>false</#if>" label="<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>:" prop="${field.propertyName}">
                        <#--</#if>-->
                        <#if field.keyFlag>
                            <el-input v-model="form.${field.propertyName}" disabled></el-input>
                        <#--<#elseif field.propertyName == 'operatorId' || field.propertyName == 'operatorName' || field.propertyName == 'createTime' || field.propertyName == 'updateTime'>-->

                        <#elseif field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formType'] == 'textarea' >
                         <el-input type="textarea" v-model="form.${field.propertyName}" autosize  placeholder="请输入<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>"
                             <#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['validateMaxLength']?? && field.customMap['customFieldInfo']['validateMaxLength']?length gt 1> :maxlength="${field.customMap['customFieldInfo']['validateMaxLength']}" </#if>
                             <#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['validateMinLength']?? && field.customMap['customFieldInfo']['validateMinLength']?length gt 1> :minlength="${field.customMap['customFieldInfo']['validateMinLength']}" </#if>
                         ></el-input>
                        <#elseif field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formType'] == 'select ' >
                      <el-select v-model="form.${field.propertyName}" placeholder="请选择">
                          <el-option key="深圳" label="选项1" value="深圳"> </el-option>
                          <el-option key="上海" label="选项2" value="上海"> </el-option>
                      </el-select>
                        <#elseif field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formType'] == 'radio' >
                          <el-radio-group v-model="form.${field.propertyName}" >
                              <el-radio :label="3">备选项</el-radio>
                              <el-radio :label="6">备选项</el-radio>
                              <el-radio :label="9">备选项</el-radio>
                          </el-radio-group>
                        <#elseif field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formType'] == 'switch' >
                         <el-switch v-model="form.${field.propertyName}"></el-switch>
                        <#elseif field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formType'] == 'switch(0,1)' >
                         <el-switch v-model="form.${field.propertyName}"  :active-value="1" :inactive-value="0"></el-switch>
                        <#elseif field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formType'] == 'checkbox' >
                        <el-checkbox-group v-model="form.${field.propertyName}">
                            <el-checkbox label="复选框 A"></el-checkbox>
                            <el-checkbox label="复选框 B"></el-checkbox>
                            <el-checkbox label="复选框 C"></el-checkbox>
                        </el-checkbox-group>
                        <#elseif field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formType'] == 'editor' >
                             <QuillEditor  v-model="form.${field.propertyName}" placeholder="请输入<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']}<#else>${field.comment}</#if>"/>
                        <#else >
                            <#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formType'] == 'date' >
                         <el-date-picker v-model="form.${field.propertyName}"  type="date" placeholder="请输入<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>"></el-date-picker>
                            <#elseif field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formType'] == 'time' >
                         <el-time-select v-model="form.${field.propertyName}"  type="date" placeholder="请输入<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>"></el-time-select>
                            <#elseif field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formType'] == 'datetime' >
                         <el-date-picker v-model="form.${field.propertyName}"  type="datetime" placeholder="请输入<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>"></el-date-picker>
                            <#elseif field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formType'] == 'number' >
                         <el-input-number v-model="form.${field.propertyName}"  placeholder="请输入<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>"
                             <#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['validateMaxValue']?? && field.customMap['customFieldInfo']['validateMaxValue']?length gt 1> :max="${field.customMap['customFieldInfo']['validateMaxValue']}" </#if>
                             <#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['validateMinValue']?? && field.customMap['customFieldInfo']['validateMinValue']?length gt 1> :min="${field.customMap['customFieldInfo']['validateMinValue']}" </#if>
                        ></el-input-number>
                            <#else >
                         <el-input v-model="form.${field.propertyName}"  placeholder="请输入<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>"
                             <#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['validateMaxLength']?? && field.customMap['customFieldInfo']['validateMaxLength']?length gt 1> :maxlength="${field.customMap['customFieldInfo']['validateMaxLength']}" </#if>
                             <#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['validateMinLength']?? && field.customMap['customFieldInfo']['validateMinLength']?length gt 1> :minlength="${field.customMap['customFieldInfo']['validateMinLength']}" </#if>
                             <#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['validateMaxValue']?? && field.customMap['customFieldInfo']['validateMaxValue']?length gt 1> :max="${field.customMap['customFieldInfo']['validateMaxValue']}" </#if>
                             <#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['validateMinValue']?? && field.customMap['customFieldInfo']['validateMinValue']?length gt 1> :min="${field.customMap['customFieldInfo']['validateMinValue']}" </#if>
                         ></el-input>
                            </#if>
                        </#if>
                        </el-form-item>
                    </#list>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisible = false" v-waves>取 消</el-button>
                    <el-button type="primary" @click="submitForm" v-waves>确 定</el-button>
                </span>
            </el-dialog>

            <el-dialog :title="titleMap[dialogStatus]" :visible.sync="updateDialogVisible" width="40%" @close="handleDialogClose">
                <el-form ref="updateDataForm" :model="updateForm" :rules="updateRules" label-width="auto" class="demo-ruleForm">
                    <#list table.fields as field>
                    <#--<#if field.propertyName != 'operatorId' && field.propertyName != 'operatorName' && field.propertyName != 'createTime' && field.propertyName != 'updateTime'>-->
                        <el-form-item v-if="<#if field.customMap['customFieldInfo']['updateFlag']?? && field.customMap['customFieldInfo']['updateFlag'] == 1 >true<#else>false</#if>" label="<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>:" prop="${field.propertyName}">
                        <#--</#if>-->
                         <#if field.keyFlag>
                            <el-input v-model="updateForm.${field.propertyName}" disabled></el-input>
                         <#--<#elseif field.propertyName == 'operatorId' || field.propertyName == 'operatorName' || field.propertyName == 'createTime' || field.propertyName == 'updateTime'>-->

                         <#elseif field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formType'] == 'textarea' >
                         <el-input type="textarea" v-model="updateForm.${field.propertyName}" autosize  placeholder="请输入<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>"
                             <#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['validateMaxLength']?? && field.customMap['customFieldInfo']['validateMaxLength']?length gt 1> :maxlength="${field.customMap['customFieldInfo']['validateMaxLength']}" </#if>
                             <#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['validateMinLength']?? && field.customMap['customFieldInfo']['validateMinLength']?length gt 1> :minlength="${field.customMap['customFieldInfo']['validateMinLength']}" </#if>
                         ></el-input>
                         <#elseif field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formType'] == 'select ' >
                      <el-select v-model="updateForm.${field.propertyName}" placeholder="请选择">
                          <el-option key="深圳" label="选项1" value="深圳"> </el-option>
                          <el-option key="上海" label="选项2" value="上海"> </el-option>
                      </el-select>
                         <#elseif field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formType'] == 'radio' >
                          <el-radio-group v-model="updateForm.${field.propertyName}" >
                              <el-radio :label="3">备选项</el-radio>
                              <el-radio :label="6">备选项</el-radio>
                              <el-radio :label="9">备选项</el-radio>
                          </el-radio-group>
                         <#elseif field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formType'] == 'editor' >
                             <QuillEditor  v-model="updateForm.${field.propertyName}" placeholder="请输入<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']}<#else>${field.comment}</#if>"/>
                         <#elseif field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formType'] == 'switch' >
                         <el-switch v-model="updateForm.${field.propertyName}"></el-switch>
                         <#elseif field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formType'] == 'switch(0,1)' >
                         <el-switch v-model="updateForm.${field.propertyName}"  :active-value="1" :inactive-value="0"></el-switch>
                         <#elseif field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formType'] == 'checkbox' >
                        <el-checkbox-group v-model="listQuery.${field.propertyName}">
                            <el-checkbox label="复选框 A"></el-checkbox>
                            <el-checkbox label="复选框 B"></el-checkbox>
                            <el-checkbox label="复选框 C"></el-checkbox>
                        </el-checkbox-group>
                         <#else >
                             <#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formType'] == 'date' >
                         <el-date-picker v-model="updateForm.${field.propertyName}"  type="date" placeholder="请输入<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>"></el-date-picker>
                             <#elseif field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formType'] == 'time' >
                         <el-time-select v-model="updateForm.${field.propertyName}"  type="date" placeholder="请输入<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>"></el-time-select>
                             <#elseif field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formType'] == 'datetime' >
                         <el-date-picker v-model="updateForm.${field.propertyName}"  type="datetime" placeholder="请输入<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>"></el-date-picker>
                             <#else >
                         <el-input v-model="updateForm.${field.propertyName}"  placeholder="请输入<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>"
                             <#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['validateMaxLength']?? && field.customMap['customFieldInfo']['validateMaxLength']?length gt 1 > :maxlength="${field.customMap['customFieldInfo']['validateMaxLength']}" </#if>
                             <#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['validateMinLength']?? && field.customMap['customFieldInfo']['validateMaxLength']?length gt 1> :minlength="${field.customMap['customFieldInfo']['validateMinLength']}" </#if>
                             <#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['validateMaxValue']?? && field.customMap['customFieldInfo']['validateMaxValue']?length gt 1> :max="${field.customMap['customFieldInfo']['validateMaxValue']}" </#if>
                             <#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['validateMinValue']?? && field.customMap['customFieldInfo']['validateMinValue']?length gt 1> :min="${field.customMap['customFieldInfo']['validateMinValue']}" </#if>
                         ></el-input>
                             </#if>
                         </#if>
                        </el-form-item>
                    </#list>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="updateDialogVisible = false" v-waves>取 消</el-button>
                    <el-button type="primary" @click="submitForm" v-waves>确 定</el-button>
                </span>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    import { get${entity}ListPage, save${entity},update${entity}, deleteById,download } from "@/api/system/${table.entityPath}";
    import waves from '@/directive/waves'
    import Pagination from '@/components/Pagination'

    export default {
        components: { Pagination},
        directives: { waves },
        filters: {
            statusFilter(status) {
                const statusMap = {
                    0: 'info',
                    1: 'success'
                }
                return statusMap[status]
            }
        },
        data() {
        return {
            showSearch: true,
            //默认查询隐藏列
            defaultHideColumns: [
                   <#if (cfg.listHideFields ? exists) >
                       <#list cfg.listHideFields as field>
                    '${field.propertyName}'<#if field_has_next >,</#if>
                       </#list>
                   </#if>
            ],
            // 列信息
            columns: [
                    <#list table.fields as field>
                    { key: ${field_index},value: '${field.propertyName}', label: '<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>', visible: true }<#if field_has_next>,</#if>
                    </#list>
            ],
            dialogVisible: false,
            updateDialogVisible: false,
            list: [],
            listLoading: true,
            total: 0,
        listQuery: {
            pageIndex: 1,
            pageSize: 10,
                 <#if (cfg.queryFields ? exists) >
                     <#list cfg.queryFields as field>
                         <#if field.queryMethod == "BETWEEN">
                        ${field.propertyName}Start: undefined,
                        ${field.propertyName}End: undefined <#if field_has_next >,</#if>
                         <#else >
                             ${field.propertyName}: undefined <#if field_has_next >,</#if>
                         </#if>
                     </#list>
                 </#if>
        },
            input: '',
                    form: {
            <#list table.fields as field>
                <#if field.propertyName == 'validState'>
                    ${field.propertyName}: 1, //<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>
                <#else>
                    ${field.propertyName}: undefined, //<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>
                </#if>
            <#--${field.propertyName}: ${((field.customMap['DEFAULT']!'')?length>0)?string((field.customMap['DEFAULT']!''),"undefined")}, //${field.comment}-->
            </#list>
            },
            updateForm: {
            <#list table.fields as field>
                <#if field.propertyName == 'validState'>
                    ${field.propertyName}: 1, //<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>
                <#else>
                    ${field.propertyName}: undefined, //<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>
                </#if>
            <#--${field.propertyName}: ${((field.customMap['DEFAULT']!'')?length>0)?string((field.customMap['DEFAULT']!''),"undefined")}, //${field.comment}-->
            </#list>
            },
            dialogStatus: "",
                    titleMap: {
                update: "编辑",
                        create: "创建"
            },
            statusMap: { 1: '正常', 0: '停用' },
            rules: {
               <#list table.fields as field>
                   ${field.propertyName}:[
                   <#if field.customMap['validate']?? && (field.customMap['validate']?size > 0)>
                       <#list field.customMap['validate'] as validate>
                           <#if (validate)?exists >
                               ${validate} <#if validate_has_next>,</#if>
                           </#if>
                       </#list>
                   </#if >
               ]<#if field_has_next>,</#if>
               </#list>
            },
            updateRules: {
                <#list table.fields as field>
                    ${field.propertyName}:[
                    <#if field.customMap['validate']?? && (field.customMap['validate']?size > 0)>
                        <#list field.customMap['validate'] as validate>
                            <#if (validate)?exists >
                                ${validate} <#if validate_has_next>,</#if>
                            </#if>
                        </#list>
                    </#if >
                ]<#if field_has_next>,</#if>
                </#list>
            }
        }
        },
        created() {
            this.getList();
        },
        methods: {
            getList() {
                this.listLoading = true;
                get${entity}ListPage(this.listQuery).then(response => {
                    this.list = response.data.list;
                this.total = response.data.total;
                this.listLoading = false;
            }).catch(err => {
                    this.listLoading = false;
                console.log(err);
            });
            },
            download() {
                download(this.listQuery).then(response => {

                }).catch(err => {

                    console.log(err);
            });
            },
            handleCreate() {
                this.resetForm();
                this.dialogStatus = "create";
                this.dialogVisible = true;
            },
            handleUpdate(row) {
                this.updateForm = Object.assign({}, row);
                this.dialogStatus = "update";
                this.updateDialogVisible = true;
            },
            handleDelete(row) {
                let _this = this;
                _this.$confirm('确定删除吗?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function () {
            <#list table.fields as field>
                <#if  (field.keyFlag) >
                    let id = row.${field.propertyName};
                </#if>
            </#list>
                    deleteById(id).then(response => {
                        if (response.code === 200) {
                        this.getList();
                        this.$message.success(response.message);
                    } else {
                        this.$message.error(response.message);
                    }
                });
                })
            },
            submitForm() {
                let _this = this;
                let refForm = _this.dialogStatus === "create" ? 'dataForm' : 'updateDataForm'
                _this.$refs[`<#noparse>${</#noparse>refForm<#noparse>}</#noparse>`].validate(valid => {
                    if (valid) {
                        this.$confirm('确定操作吗?', '提示', {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning'
                        }).then(function () {
                            if(_this.dialogStatus === "create"){
                                save${entity}(_this.form).then(response => {
                                    if (response.code === 200) {
                                    _this.getList();
                                    _this.$message.success(response.message);
                                    _this.dialogVisible = false;
                                } else {
                                    _this.$message.error(response.message);
                                }
                            }).catch(err => { console.log(err);  })

                            } else {
                                update${entity}(_this.updateForm).then(response => {
                                    if (response.code === 200) {
                                    _this.getList();
                                    _this.$message.success(response.message);
                                    _this.updateDialogVisible = false;
                                } else {
                                    _this.$message.error(response.message);
                                }
                            }).catch(err => { console.log(err);  })
                            }
                        })
                    }
                })
            },
            resetForm() {
                this.form = {
            <#list table.fields as field>
                <#if field.propertyName == 'validState'>
                    ${field.propertyName}: 1, //<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>
                <#else>
                    ${field.propertyName}: undefined, //<#if field.customMap['customFieldInfo']?? && field.customMap['customFieldInfo']['formLabel']?? >${field.customMap['customFieldInfo']['formLabel']} <#else>${field.comment}</#if>
                </#if>
            </#list>
            }
            },
            // 监听dialog关闭时的处理事件
            handleDialogClose(){
                let refForm = this.dialogStatus === "create" ? 'dataForm' : 'updateDataForm'
                if(this.$refs[[`<#noparse>${</#noparse>refForm<#noparse>}</#noparse>`]]){
                    this.$refs[[`<#noparse>${</#noparse>refForm<#noparse>}</#noparse>`]].clearValidate(); // 清除整个表单的校验
                }
            }
        }
    }
</script>

<style  rel="stylesheet/scss" lang="scss">

    .page-footer{
        margin-top: 20px;
        width: 100%;
        height: 50px;
    }
    .filter-container {
        padding-bottom: 10px;
    .filter-item {
        display: inline-block;
        vertical-align: middle;
        margin-bottom: 10px;
    }
    }
</style>