CREATE TABLE dw_dim.partner_user_source_link_map (
source_table_name varchar(50) DEFAULT NULL,
source_code varchar(100) DEFAULT NULL,
user_source_link_id int4 DEFAULT NULL,
user_source_link_name varchar(100) DEFAULT NULL,
created_busi_date int4 DEFAULT NULL,
last_modified_busi_date int4 DEFAULT NULL,
creation_date timestamp DEFAULT NULL,
created_by varchar(200) DEFAULT NULL,
last_modification_date timestamp DEFAULT NULL,
last_modified_by varchar(200) DEFAULT NULL
)
WITH (OIDS=FALSE)
;

ALTER TABLE dw_dim.partner_user_source_link_map OWNER TO postgres;

CREATE TABLE his_ht_sb81.ods_ocrm_sbj_habit (
pty_id varchar(100) DEFAULT NULL,
attrib_name varchar(100) DEFAULT NULL,
attrib_val varchar(500) DEFAULT NULL,
upd_dt varchar(8) DEFAULT NULL,
end_dt varchar(8) DEFAULT NULL,
data_dt varchar(8) DEFAULT NULL,
desc_text varchar(255) DEFAULT NULL,
parameter varchar(100) DEFAULT NULL,
source_cd varchar(50) DEFAULT NULL,
valid_cd varchar(30) DEFAULT NULL,
dc_business_date int4 DEFAULT NULL,
dc_collect_date timestamp(6) DEFAULT NULL,
dc_program_id int4 DEFAULT NULL,
dc_server_id int4 DEFAULT NULL
)
WITH (OIDS=FALSE)
;

ALTER TABLE his_ht_sb81.ods_ocrm_sbj_habit OWNER TO postgres;

CREATE TABLE his_ht_sb81.serv_cust_xsb_avgaset (
dc_business_date numeric(8) DEFAULT NULL,
dc_collect_date timestamp DEFAULT NULL,
dc_program_id numeric(32) DEFAULT NULL,
dc_server_id numeric(32) DEFAULT NULL,
data_dt varchar(32) DEFAULT NULL,
cust_id varchar(128) DEFAULT NULL,
avg_aset numeric(20,4) DEFAULT NULL
)
WITH (OIDS=FALSE)
;

ALTER TABLE his_ht_sb81.serv_cust_xsb_avgaset OWNER TO postgres;


CREATE TABLE report.neeq_risk_result (
period_id int4 DEFAULT NULL,
risk_test_date int4 DEFAULT NULL,
client_id varchar(20) DEFAULT NULL,
end_date int4 DEFAULT NULL,
last_date int4 DEFAULT NULL,
asset numeric(20,4) DEFAULT 0
)
WITH (OIDS=FALSE)
;

ALTER TABLE report.neeq_risk_result OWNER TO postgres;


CREATE TABLE report.neeq_risk_result_bo (
period_id int4 DEFAULT NULL,
up_no int4 DEFAULT NULL,
branch_no int4 DEFAULT NULL,
up_name varchar(200) DEFAULT NULL,
branch_name varchar(200) DEFAULT NULL,
client_id varchar(20) DEFAULT NULL,
client_name varchar(200) DEFAULT NULL,
neeq_open_date int4 DEFAULT NULL,
latest_date int4 DEFAULT NULL,
check_asset numeric DEFAULT NULL,
holer_status varchar(200) DEFAULT NULL,
remark varchar(200) DEFAULT NULL
)
WITH (OIDS=FALSE)
;

ALTER TABLE report.neeq_risk_result_bo OWNER TO postgres;

CREATE TABLE report.neeq_risk_result_flag (
period_id int4 DEFAULT NULL,
date_flag int4 DEFAULT NULL
)
WITH (OIDS=FALSE)
;

ALTER TABLE report.neeq_risk_result_flag OWNER TO postgres;


CREATE TABLE report.net_recomm_open_status (
period_id int4 DEFAULT NULL,
mobile_phone varchar(30) DEFAULT NULL,
client_id varchar(12) DEFAULT NULL,
client_name varchar(100) DEFAULT NULL,
recom_mobile_phone varchar(30) DEFAULT NULL,
recom_client_id varchar(12) DEFAULT NULL,
recom_client_name varchar(100) DEFAULT NULL,
client_status int4 DEFAULT 1,
is_new int4 DEFAULT 0
)
WITH (OIDS=FALSE)
;

ALTER TABLE report.net_recomm_open_status OWNER TO postgres;

CREATE TABLE report.net_recomm_status_result (
period_id int4 DEFAULT NULL,
mobile_phone varchar(30) DEFAULT NULL,
client_id varchar(12) DEFAULT NULL,
client_name varchar(100) DEFAULT NULL,
recom_mobile_phone varchar(30) DEFAULT NULL,
recom_client_id varchar(12) DEFAULT NULL,
recom_client_name varchar(100) DEFAULT NULL,
client_status int4 DEFAULT NULL,
tfrom varchar(200) DEFAULT NULL,
tfrom_page varchar(200) DEFAULT NULL
)
WITH (OIDS=FALSE)
;

ALTER TABLE report.net_recomm_status_result OWNER TO postgres;


CREATE TABLE report.net_recomm_status_result_his (
period_id int4 DEFAULT NULL,
mobile_phone varchar(30) DEFAULT NULL,
client_id varchar(12) DEFAULT NULL,
client_name varchar(100) DEFAULT NULL,
recom_mobile_phone varchar(30) DEFAULT NULL,
recom_client_id varchar(12) DEFAULT NULL,
recom_client_name varchar(100) DEFAULT NULL,
client_status int4 DEFAULT NULL,
tfrom varchar(200) DEFAULT NULL,
tfrom_page varchar(200) DEFAULT NULL
)
WITH (OIDS=FALSE)
;

ALTER TABLE report.net_recomm_status_result_his OWNER TO postgres;

CREATE TABLE report.t_fuel_cards_user (
id int4 DEFAULT NULL,
mobile_no varchar(25) DEFAULT NULL,
create_time varchar(20) DEFAULT NULL,
is_receive int4 DEFAULT 0,
fuel_card_id int4 DEFAULT NULL
)
WITH (OIDS=FALSE)
;

ALTER TABLE report.t_fuel_cards_user OWNER TO postgres;

CREATE TABLE report.tifstockpool_check_detail (
period_id int4 DEFAULT NULL,
system_name varchar(100) DEFAULT NULL,
name varchar(100) DEFAULT NULL,
stockcode varchar(100) DEFAULT NULL,
marketno varchar(100) DEFAULT NULL,
remark varchar(100) DEFAULT NULL,
optype varchar(100) DEFAULT NULL,
name_opp varchar(100) DEFAULT NULL,
stockcode_opp varchar(100) DEFAULT NULL,
marketno_opp varchar(100) DEFAULT NULL,
remark_opp varchar(100) DEFAULT NULL,
optype_opp varchar(100) DEFAULT NULL
)
WITH (OIDS=FALSE)
;

ALTER TABLE report.tifstockpool_check_detail OWNER TO postgres;


CREATE TABLE report.tifstockpool_check_result (
period_id int4 DEFAULT NULL,
system_name varchar(100) DEFAULT NULL,
check_result varchar(100) DEFAULT NULL
)
WITH (OIDS=FALSE)
;

ALTER TABLE report.tifstockpool_check_result OWNER TO postgres;

CREATE TABLE src_ht_hs08_hsasset.ht_clientsignjour (
dc_business_date int4 DEFAULT NULL,
dc_collect_date timestamp(6) DEFAULT NULL,
dc_program_id int4 DEFAULT NULL,
dc_server_id int4 DEFAULT NULL,
init_date numeric(10) DEFAULT NULL NOT NULL,
curr_date numeric(10) DEFAULT NULL NOT NULL,
curr_time numeric(10) DEFAULT NULL NOT NULL,
op_entrust_way char(1) DEFAULT NULL NOT NULL,
op_station varchar(255) DEFAULT NULL NOT NULL,
operator_no varchar(18) DEFAULT NULL NOT NULL,
log_serial_no numeric(10) DEFAULT NULL NOT NULL,
branch_no numeric(10) DEFAULT NULL NOT NULL,
client_id varchar(18) DEFAULT NULL NOT NULL,
fund_account varchar(18) DEFAULT NULL NOT NULL,
agreement_kind varchar(12) DEFAULT NULL NOT NULL,
agreement_version varchar(10) DEFAULT NULL NOT NULL,
remark varchar(2000) DEFAULT NULL NOT NULL,
position_str varchar(100) DEFAULT NULL NOT NULL,
exchange_type varchar(4) DEFAULT NULL NOT NULL,
stock_account varchar(20) DEFAULT NULL NOT NULL,
prodta_no varchar(24) DEFAULT NULL NOT NULL,
prod_code varchar(32) DEFAULT NULL NOT NULL,
prod_account varchar(18) DEFAULT NULL NOT NULL,
business_group varchar(16) DEFAULT NULL,
data_source char(1) DEFAULT NULL,
business_id varchar(32) DEFAULT NULL
)
WITH (OIDS=FALSE)
;

ALTER TABLE src_ht_hs08_hsasset.ht_clientsignjour OWNER TO postgres;


CREATE TABLE src_ht_hs08_hsasset.sstaccount (
dc_business_date int4 DEFAULT NULL,
dc_collect_date timestamp(6) DEFAULT NULL,
dc_program_id int4 DEFAULT NULL,
dc_server_id int4 DEFAULT NULL,
client_id varchar(18) DEFAULT NULL,
fund_account varchar(18) DEFAULT NULL,
exchange_type varchar(4) DEFAULT NULL,
stock_account varchar(20) DEFAULT NULL,
branch_no numeric(10) DEFAULT NULL,
organ_flag char(1) DEFAULT NULL,
asset_balance numeric(19,2) DEFAULT NULL,
first_exchdate numeric(10) DEFAULT NULL,
sub_risk_date numeric(10) DEFAULT NULL,
register_date numeric(10) DEFAULT NULL,
sstacct_status char(1) DEFAULT NULL,
right_open_date numeric(10) DEFAULT NULL,
remark varchar(2000) DEFAULT NULL,
position_str varchar(100) DEFAULT NULL,
report_date numeric(10) DEFAULT NULL,
op_branch_no numeric(10) DEFAULT NULL,
operator_no varchar(18) DEFAULT NULL,
sst_report_type char(1) DEFAULT NULL,
register_fund numeric(16,4) DEFAULT NULL
)
WITH (OIDS=FALSE)
;

ALTER TABLE src_ht_hs08_hsasset.sstaccount OWNER TO postgres;



CREATE TABLE src_ht_hscif2.lcfxckh (
dc_business_date int4 DEFAULT NULL,
dc_collect_date timestamp(6) DEFAULT NULL,
dc_program_id int4 DEFAULT NULL,
dc_server_id int4 DEFAULT NULL,
id numeric(16) DEFAULT NULL NOT NULL,
instid numeric(16) DEFAULT NULL,
khfs numeric(12) DEFAULT NULL,
jgbz numeric(12) DEFAULT NULL,
yyb numeric(12) DEFAULT NULL,
khly numeric(12) DEFAULT NULL,
khxm varchar(160) DEFAULT NULL,
khqc varchar(80) DEFAULT NULL,
jzr numeric(12) DEFAULT NULL,
khxzr numeric(12) DEFAULT NULL,
zjlb numeric(12) DEFAULT NULL,
zjbh varchar(30) DEFAULT NULL,
xb numeric(12) DEFAULT NULL,
csrq numeric(8) DEFAULT NULL,
zjqsrq varchar(8) DEFAULT NULL,
zjjzrq varchar(8) DEFAULT NULL,
zjdz varchar(100) DEFAULT NULL,
zjdzyb varchar(6) DEFAULT NULL,
zjfzjg varchar(60) DEFAULT NULL,
zjzp varchar(100) DEFAULT NULL,
zjyzly numeric(12) DEFAULT NULL,
edz int4 DEFAULT NULL,
yyzznjrq numeric(8) DEFAULT NULL,
dz varchar(100) DEFAULT NULL,
yzbm varchar(6) DEFAULT NULL,
jtdz varchar(100) DEFAULT NULL,
jtdzyb varchar(6) DEFAULT NULL,
jtdh varchar(20) DEFAULT NULL,
sj varchar(30) DEFAULT NULL,
dh varchar(20) DEFAULT NULL,
cz varchar(20) DEFAULT NULL,
email varchar(50) DEFAULT NULL,
gzdw varchar(60) DEFAULT NULL,
gzdwdz varchar(100) DEFAULT NULL,
gzdwyb varchar(6) DEFAULT NULL,
gzdwdh varchar(20) DEFAULT NULL,
qq varchar(20) DEFAULT NULL,
msn varchar(50) DEFAULT NULL,
hyzk numeric(12) DEFAULT NULL,
zydm numeric(12) DEFAULT NULL,
xl numeric(12) DEFAULT NULL,
mzdm numeric(12) DEFAULT NULL,
jg varchar(20) DEFAULT NULL,
gj numeric(12) DEFAULT NULL,
province varchar(30) DEFAULT NULL,
city varchar(6) DEFAULT NULL,
sec varchar(6) DEFAULT NULL,
tbsm varchar(255) DEFAULT NULL,
zwdm numeric(12) DEFAULT NULL,
njsr numeric(16,2) DEFAULT NULL,
zgzslx numeric(12) DEFAULT NULL,
zsbh varchar(30) DEFAULT NULL,
cysj numeric(8) DEFAULT NULL,
jsfs numeric(12) DEFAULT NULL,
lxfs varchar(300) DEFAULT NULL,
llpl numeric(12) DEFAULT NULL,
gtkhly numeric(12) DEFAULT NULL,
qsxy varchar(300) DEFAULT NULL,
fxqhylb numeric(12) DEFAULT NULL,
xqfxdj numeric(12) DEFAULT NULL,
qyxz numeric(12) DEFAULT NULL,
hydm numeric(12) DEFAULT NULL,
frlb numeric(12) DEFAULT NULL,
ywmc varchar(100) DEFAULT NULL,
jgwzdz varchar(60) DEFAULT NULL,
jggsdh varchar(20) DEFAULT NULL,
jgzcdz varchar(100) DEFAULT NULL,
jgzcrq numeric(8) DEFAULT NULL,
jgjyfw varchar(800) DEFAULT NULL,
jgzcbz numeric(12) DEFAULT NULL,
jgzczb numeric(16,2) DEFAULT NULL,
jgzgb numeric(12) DEFAULT NULL,
jgltgb numeric(12) DEFAULT NULL,
zzjgdm varchar(16) DEFAULT NULL,
zzjgdmqsrq numeric(8) DEFAULT NULL,
zzjgdmjzrq numeric(8) DEFAULT NULL,
zzjgdmfzjg varchar(30) DEFAULT NULL,
zzjgdmnjrq numeric(8) DEFAULT NULL,
gsswdjz varchar(60) DEFAULT NULL,
gsswdjzqsrq numeric(8) DEFAULT NULL,
gsswdjzjzrq numeric(8) DEFAULT NULL,
gsswdjzfzjg varchar(30) DEFAULT NULL,
gsswdjznjrq numeric(8) DEFAULT NULL,
dsswdjz varchar(60) DEFAULT NULL,
dsswdjzqsrq numeric(8) DEFAULT NULL,
dsswdjzjzrq numeric(8) DEFAULT NULL,
dsswdjzfzjg varchar(30) DEFAULT NULL,
dsswdjznjrq numeric(8) DEFAULT NULL,
frdbxm varchar(1024) DEFAULT NULL,
frdbdh varchar(20) DEFAULT NULL,
frdbzjlb numeric(12) DEFAULT NULL,
frdbzjbh varchar(30) DEFAULT NULL,
frdbzjqsrq varchar(8) DEFAULT NULL,
frdbzjjzrq varchar(8) DEFAULT NULL,
jbrxm varchar(16) DEFAULT NULL,
jbrdh varchar(20) DEFAULT NULL,
jbrzjlb numeric(12) DEFAULT NULL,
jbrzjbh varchar(30) DEFAULT NULL,
jbrzjqsrq varchar(8) DEFAULT NULL,
jbrzjjzrq varchar(8) DEFAULT NULL,
fzrxm varchar(80) DEFAULT NULL,
fzrdh varchar(20) DEFAULT NULL,
fzrzjlb numeric(12) DEFAULT NULL,
fzrzjbh varchar(30) DEFAULT NULL,
fzrzjqsrq varchar(8) DEFAULT NULL,
fzrzjjzrq varchar(8) DEFAULT NULL,
khmb numeric(16) DEFAULT NULL,
zcsx numeric(12) DEFAULT NULL,
khfl numeric(12) DEFAULT NULL,
khfz numeric(16) DEFAULT NULL,
wtfs varchar(300) DEFAULT NULL,
khqx varchar(300) DEFAULT NULL,
khxz varchar(300) DEFAULT NULL,
gdqx varchar(300) DEFAULT NULL,
gdxz varchar(300) DEFAULT NULL,
cblx numeric(12) DEFAULT NULL,
lllb numeric(16) DEFAULT NULL,
khgfxx varchar(300) DEFAULT NULL,
gskhlx numeric(12) DEFAULT NULL,
fxysxx varchar(300) DEFAULT NULL,
yxbz varchar(300) DEFAULT NULL,
cpbz numeric(12) DEFAULT NULL,
khkh varchar(30) DEFAULT NULL,
khh varchar(12) DEFAULT NULL,
fxcsnl numeric(12) DEFAULT NULL,
kfyyb numeric(12) DEFAULT NULL,
kfgy numeric(12) DEFAULT NULL,
sftb numeric(12) DEFAULT NULL,
jymm varchar(32) DEFAULT NULL,
zjmm varchar(32) DEFAULT NULL,
fwmm varchar(32) DEFAULT NULL,
sqjjzh varchar(1024) DEFAULT NULL,
gdkh_sh numeric(12) DEFAULT NULL,
gddj_sh varchar(10) DEFAULT NULL,
gdkh_hb numeric(12) DEFAULT NULL,
gddj_hb varchar(10) DEFAULT NULL,
gdkh_sz numeric(12) DEFAULT NULL,
gddj_sz varchar(10) DEFAULT NULL,
gdkh_sb numeric(12) DEFAULT NULL,
gddj_sb varchar(10) DEFAULT NULL,
gdkh_ta numeric(12) DEFAULT NULL,
gddj_ta varchar(10) DEFAULT NULL,
gdkh_tu numeric(12) DEFAULT NULL,
gddj_tu varchar(10) DEFAULT NULL,
gdjyqx_sh varchar(300) DEFAULT NULL,
gdjyqx_hb varchar(300) DEFAULT NULL,
gdjyqx_sz varchar(300) DEFAULT NULL,
gdjyqx_sb varchar(300) DEFAULT NULL,
gdjyqx_ta varchar(300) DEFAULT NULL,
gdjyqx_tu varchar(300) DEFAULT NULL,
shzdjy int4 DEFAULT NULL,
hbzdjy int4 DEFAULT NULL,
sfwlfw int4 DEFAULT NULL,
wlfwmm varchar(32) DEFAULT NULL,
cgyh varchar(4) DEFAULT NULL,
cgyhzh varchar(30) DEFAULT NULL,
cgyhmm varchar(30) DEFAULT NULL,
yhdm_usd varchar(4) DEFAULT NULL,
djfs_usd numeric(12) DEFAULT NULL,
yhzh_usd varchar(30) DEFAULT NULL,
yhmm_usd varchar(30) DEFAULT NULL,
yhdm_hkd varchar(4) DEFAULT NULL,
djfs_hkd numeric(12) DEFAULT NULL,
yhzh_hkd varchar(30) DEFAULT NULL,
yhmm_hkd varchar(30) DEFAULT NULL,
khzp varchar(100) DEFAULT NULL,
khsp varchar(100) DEFAULT NULL,
shzt numeric(12) DEFAULT NULL,
step numeric(12) DEFAULT NULL,
djr numeric(12) DEFAULT NULL,
shgy numeric(12) DEFAULT NULL,
sqrq numeric(8) DEFAULT NULL,
sqsj varchar(8) DEFAULT NULL,
czzd varchar(80) DEFAULT NULL,
cljgsm varchar(255) DEFAULT NULL,
iby1 numeric(12) DEFAULT NULL,
iby2 numeric(12) DEFAULT NULL,
iby3 numeric(12) DEFAULT NULL,
cby1 varchar(30) DEFAULT NULL,
cby2 varchar(30) DEFAULT NULL,
cby3 varchar(30) DEFAULT NULL,
sfyzwt numeric(12) DEFAULT NULL,
sfyzda varchar(60) DEFAULT NULL,
zkmblx numeric(16) DEFAULT NULL,
zczh varchar(20) DEFAULT NULL,
khzd numeric(12) DEFAULT NULL,
hfrq numeric(8) DEFAULT NULL,
hfsj varchar(8) DEFAULT NULL,
hfgydm varchar(30) DEFAULT NULL,
hfgyxm varchar(30) DEFAULT NULL,
hfjg numeric(12) DEFAULT NULL,
hfjgsm varchar(500) DEFAULT NULL,
hftj numeric(12) DEFAULT NULL
)
WITH (OIDS=FALSE)
;

ALTER TABLE src_ht_hscif2.lcfxckh OWNER TO postgres;

CREATE TABLE src_ht_hscif2.lcfxckh_zjb (
id numeric(16) DEFAULT NULL NOT NULL,
sj varchar(30) DEFAULT NULL,
yybbm varchar(30) DEFAULT NULL,
khh varchar(12) DEFAULT NULL,
zjzh varchar(20) DEFAULT NULL,
khxm varchar(30) DEFAULT NULL,
khqc varchar(120) DEFAULT NULL,
yxrygh varchar(12) DEFAULT NULL,
gxrq varchar(8) DEFAULT NULL,
lcfxckh_id numeric(16) DEFAULT NULL,
csrq numeric(8) DEFAULT NULL,
zjlb numeric(12) DEFAULT NULL,
zjbh varchar(30) DEFAULT NULL,
xb numeric(12) DEFAULT NULL,
zjqsrq varchar(8) DEFAULT NULL,
zjjzrq varchar(8) DEFAULT NULL,
zjdz varchar(100) DEFAULT NULL,
zjfzjg varchar(60) DEFAULT NULL,
dz varchar(100) DEFAULT NULL,
yzbm varchar(6) DEFAULT NULL,
email varchar(50) DEFAULT NULL,
dh varchar(20) DEFAULT NULL,
zydm numeric(12) DEFAULT NULL,
xl numeric(12) DEFAULT NULL,
yjtc varchar(255) DEFAULT NULL,
jymm varchar(32) DEFAULT NULL,
zjmm varchar(32) DEFAULT NULL,
sqjjzh varchar(1024) DEFAULT NULL,
gdkh_sh numeric(12) DEFAULT NULL,
gdkh_sz numeric(12) DEFAULT NULL,
cgyh varchar(4) DEFAULT NULL,
cgyhzh varchar(30) DEFAULT NULL,
wjid numeric(12) DEFAULT NULL,
tmdac varchar(1000) DEFAULT NULL,
htxystr varchar(2000) DEFAULT NULL,
yxstr varchar(2000) DEFAULT NULL,
dzzsbh varchar(100) DEFAULT NULL,
delxrxm varchar(60) DEFAULT NULL,
delxrdh varchar(20) DEFAULT NULL,
ykhrgx numeric(12) DEFAULT NULL,
gj numeric(12) DEFAULT NULL,
cybkt numeric(12) DEFAULT NULL,
hfwjid numeric(12) DEFAULT NULL,
hftmdac varchar(1000) DEFAULT NULL,
wtfs varchar(300) DEFAULT NULL,
zjyzly numeric(12) DEFAULT NULL,
gtkhly numeric(12) DEFAULT NULL,
khsp varchar(100) DEFAULT NULL,
mzdm numeric(12) DEFAULT NULL,
gddj_sh varchar(10) DEFAULT NULL,
gddj_sz varchar(10) DEFAULT NULL,
jzfs numeric(12) DEFAULT NULL,
spsftg numeric(12) DEFAULT NULL,
spshyj varchar(300) DEFAULT NULL,
sprlgy numeric(12) DEFAULT NULL,
khzd numeric(12) DEFAULT NULL,
spshyjbz varchar(300) DEFAULT NULL,
dc_business_date int4 DEFAULT NULL,
dc_collect_date timestamp(6) DEFAULT NULL,
dc_program_id int4 DEFAULT NULL,
dc_server_id int4 DEFAULT NULL,
CONSTRAINT lcfxckh_zjb_pkey PRIMARY KEY (id)
)
WITH (OIDS=FALSE)
;

ALTER TABLE src_ht_hscif2.lcfxckh_zjb OWNER TO postgres;

CREATE TABLE src_ht_risk.tifstockpool (
dc_business_date numeric(8) DEFAULT NULL,
dc_collect_date timestamp DEFAULT NULL,
dc_program_id numeric(32) DEFAULT NULL,
dc_server_id numeric(32) DEFAULT NULL,
name varchar(64) DEFAULT NULL,
stockcode varchar(20) DEFAULT NULL,
marketno varchar(16) DEFAULT NULL,
remark varchar(200) DEFAULT NULL,
optype char(1) DEFAULT NULL
)
WITH (OIDS=FALSE)
;

ALTER TABLE src_ht_risk.tifstockpool OWNER TO postgres;

CREATE TABLE src_ht_sb81.serv_pty_cust_conv (
dc_business_date numeric DEFAULT NULL,
dc_collect_date timestamp(6) DEFAULT NULL,
dc_program_id numeric DEFAULT NULL,
dc_server_id numeric DEFAULT NULL,
sys_id varchar(12) DEFAULT NULL,
sor_pty_id varchar(32) DEFAULT NULL,
rec_act_cd varchar(12) DEFAULT NULL,
org_id varchar(32) DEFAULT NULL,
data_dt varchar(8) DEFAULT NULL,
pty_id varchar(32) DEFAULT NULL
)
WITH (OIDS=FALSE)
;

ALTER TABLE src_ht_sb81.serv_pty_cust_conv OWNER TO postgres;

CREATE TABLE src_ht_sb81.swap_tifstockpool (
dc_business_date numeric(8) DEFAULT NULL,
dc_collect_date timestamp DEFAULT NULL,
dc_program_id numeric(32) DEFAULT NULL,
dc_server_id numeric(32) DEFAULT NULL,
swap_dt varchar(32) DEFAULT NULL,
name varchar(256) DEFAULT NULL,
stockcode varchar(80) DEFAULT NULL,
marketno varchar(64) DEFAULT NULL,
remark varchar(800) DEFAULT NULL,
optype char(4) DEFAULT NULL
)
WITH (OIDS=FALSE)
;

ALTER TABLE src_ht_sb81.swap_tifstockpool OWNER TO postgres;


CREATE TABLE src_ht_zlfin_mobile.t_account_ext_info (
mobile_phone varchar(20) DEFAULT NULL NOT NULL,
attribute_key varchar(200) DEFAULT NULL NOT NULL,
attribute_value varchar(4000) DEFAULT NULL,
operator_time date DEFAULT NULL,
dc_business_date int4 DEFAULT NULL,
dc_collect_date timestamp(6) DEFAULT NULL,
dc_program_id int4 DEFAULT NULL,
dc_server_id int4 DEFAULT NULL,
CONSTRAINT t_account_ext_info_pkey PRIMARY KEY (mobile_phone, attribute_key)
)
WITH (OIDS=FALSE)
;

ALTER TABLE src_ht_zlfin_mobile.t_account_ext_info OWNER TO postgres;


CREATE TABLE src_ht_zlfin_mobile.t_account_ext_info_kh (
dc_business_date numeric(8) DEFAULT NULL,
dc_collect_date timestamp DEFAULT NULL,
dc_program_id numeric(32) DEFAULT NULL,
dc_server_id numeric(32) DEFAULT NULL,
mobile_phone varchar(20) DEFAULT NULL NOT NULL,
attribute_key varchar(200) DEFAULT NULL NOT NULL,
attribute_value varchar(4000) DEFAULT NULL,
operator_time timestamp DEFAULT NULL,
CONSTRAINT t_account_ext_info_kh_pkey PRIMARY KEY (mobile_phone, attribute_key)
)
WITH (OIDS=FALSE)
;

ALTER TABLE src_ht_zlfin_mobile.t_account_ext_info_kh OWNER TO postgres;


CREATE TABLE src_ht_zlfin_mobile.t_account_info (
dc_business_date int4 DEFAULT NULL,
dc_collect_date timestamp(6) DEFAULT NULL,
dc_program_id int4 DEFAULT NULL,
dc_server_id int4 DEFAULT NULL,
mobile_phone varchar(20) DEFAULT NULL,
step varchar(100) DEFAULT NULL,
identify_code varchar(20) DEFAULT NULL,
identify_code_timestamp date DEFAULT NULL,
ca_real_name varchar(100) DEFAULT NULL,
ca_card_no varchar(100) DEFAULT NULL,
ca_gender_code varchar(20) DEFAULT NULL,
ca_gender_desc varchar(20) DEFAULT NULL,
ca_nationality_code varchar(20) DEFAULT NULL,
ca_nationality_desc varchar(100) DEFAULT NULL,
ca_post_code varchar(20) DEFAULT NULL,
ca_e_mail varchar(100) DEFAULT NULL,
ca_contact_info varchar(100) DEFAULT NULL,
ca_province_code varchar(20) DEFAULT NULL,
ca_province_desc varchar(100) DEFAULT NULL,
ca_city_code varchar(20) DEFAULT NULL,
ca_city_desc varchar(100) DEFAULT NULL,
ca_street_desc varchar(500) DEFAULT NULL,
ca_agreements varchar(100) DEFAULT NULL,
ca_video_passed varchar(100) DEFAULT NULL,
ca_install_result varchar(20) DEFAULT NULL,
acc_agreements varchar(100) DEFAULT NULL,
acc_province_code varchar(20) DEFAULT NULL,
acc_province_desc varchar(100) DEFAULT NULL,
acc_branch_code varchar(20) DEFAULT NULL,
acc_branch_desc varchar(200) DEFAULT NULL,
acc_manager_no varchar(50) DEFAULT NULL,
acc_package varchar(20) DEFAULT NULL,
acc_real_name varchar(100) DEFAULT NULL,
acc_gender_code varchar(20) DEFAULT NULL,
acc_gender_desc varchar(20) DEFAULT NULL,
acc_certificate_type varchar(20) DEFAULT NULL,
acc_certificate_desc varchar(50) DEFAULT NULL,
acc_certificate_no varchar(100) DEFAULT NULL,
acc_certificate_begin varchar(50) DEFAULT NULL,
acc_certificate_end varchar(50) DEFAULT NULL,
acc_certificate_issuer varchar(100) DEFAULT NULL,
acc_certificate_address varchar(200) DEFAULT NULL,
acc_education_desc varchar(50) DEFAULT NULL,
acc_delegate_method varchar(100) DEFAULT NULL,
acc_secret_question_desc varchar(100) DEFAULT NULL,
acc_secret_answer varchar(200) DEFAULT NULL,
acc_risk_assess_answer varchar(500) DEFAULT NULL,
acc_risk_assess_grade varchar(100) DEFAULT NULL,
acc_bank_code varchar(20) DEFAULT NULL,
acc_bank_desc varchar(100) DEFAULT NULL,
acc_bank_card_no varchar(100) DEFAULT NULL,
acc_accounts varchar(100) DEFAULT NULL,
ca_card_type varchar(20) DEFAULT NULL,
acc_secret_question_id varchar(20) DEFAULT NULL,
acc_education_id varchar(20) DEFAULT NULL,
acc_deliver_province_code varchar(20) DEFAULT NULL,
acc_deliver_province_desc varchar(100) DEFAULT NULL,
acc_deliver_city_code varchar(20) DEFAULT NULL,
acc_deliver_city_desc varchar(100) DEFAULT NULL,
acc_deliver_address varchar(100) DEFAULT NULL,
acc_gddj_sh varchar(100) DEFAULT NULL,
acc_gddj_sz varchar(100) DEFAULT NULL,
acc_bank_card_pic_base64 text DEFAULT NULL,
acc_bank_card_video_str varchar(4000) DEFAULT NULL,
ca_room_no varchar(200) DEFAULT NULL,
acc_deliver_post_code varchar(20) DEFAULT NULL,
ca_card_pic_str varchar(4000) DEFAULT NULL,
acc_city_code varchar(20) DEFAULT NULL,
acc_city_desc varchar(100) DEFAULT NULL,
acc_risk_investtype varchar(200) DEFAULT NULL,
acc_risk_investability varchar(50) DEFAULT NULL,
acc_risk_wealthcycle varchar(50) DEFAULT NULL,
acc_risk_totalscore int4 DEFAULT NULL,
acc_risk_abilityscore int4 DEFAULT NULL,
acc_kh_lsh varchar(200) DEFAULT NULL,
ca_id_card_front_pic_base64 text DEFAULT NULL,
ca_id_card_front_pic_video_str varchar(4000) DEFAULT NULL,
ca_id_card_back_pic_base64 text DEFAULT NULL,
ca_id_card_back_pic_video_str varchar(4000) DEFAULT NULL,
acc_job_id varchar(100) DEFAULT NULL,
acc_job_desc varchar(100) DEFAULT NULL,
phone_model varchar(500) DEFAULT NULL,
surf_method varchar(500) DEFAULT NULL,
cert_dn varchar(200) DEFAULT NULL,
is_modifiable varchar(10) DEFAULT NULL,
wj_answer text DEFAULT NULL,
wj_answer_flag varchar(10) DEFAULT NULL,
cert_sn varchar(200) DEFAULT NULL,
client_no varchar(100) DEFAULT NULL,
fund_account varchar(100) DEFAULT NULL,
tfrom varchar(100) DEFAULT NULL,
flags varchar(100) DEFAULT NULL,
issuer_dn varchar(200) DEFAULT NULL,
ca_auxiliary_pic1_video_str varchar(4000) DEFAULT NULL,
ca_auxiliary_pic1_type varchar(10) DEFAULT NULL,
ca_auxiliary_pic2_video_str varchar(4000) DEFAULT NULL,
ca_auxiliary_pic2_type varchar(10) DEFAULT NULL,
agree_sign_time date DEFAULT NULL
)
WITH (OIDS=FALSE)
;

ALTER TABLE src_ht_zlfin_mobile.t_account_info OWNER TO postgres;

CREATE TABLE src_ht_zlfin_mobile.t_account_info_kh (
mobile_phone varchar(20) DEFAULT NULL NOT NULL,
step varchar(100) DEFAULT NULL,
identify_code varchar(20) DEFAULT NULL,
identify_code_timestamp timestamp DEFAULT NULL,
tfrom varchar(100) DEFAULT NULL,
client_no varchar(100) DEFAULT NULL,
fund_account varchar(100) DEFAULT NULL,
acc_kh_lsh varchar(200) DEFAULT NULL,
dc_business_date int4 DEFAULT NULL,
dc_collect_date timestamp(6) DEFAULT NULL,
dc_program_id int4 DEFAULT NULL,
dc_server_id int4 DEFAULT NULL,
CONSTRAINT t_account_info_kh_pkey PRIMARY KEY (mobile_phone)
)
WITH (OIDS=FALSE)
;

ALTER TABLE src_ht_zlfin_mobile.t_account_info_kh OWNER TO postgres;

CREATE TABLE src_ht_zlfin_mobile.tifstockpool (
dc_business_date numeric(8) DEFAULT NULL,
dc_collect_date timestamp DEFAULT NULL,
dc_program_id numeric(32) DEFAULT NULL,
dc_server_id numeric(32) DEFAULT NULL,
name varchar(64) DEFAULT NULL,
stockcode varchar(20) DEFAULT NULL,
marketno varchar(16) DEFAULT NULL,
remark varchar(200) DEFAULT NULL,
optype char(1) DEFAULT NULL
)
WITH (OIDS=FALSE)
;

ALTER TABLE src_ht_zlfin_mobile.tifstockpool OWNER TO postgres;

