

-- ----------------------------
-- sequence structure for ${modelName}_${table.sqlName}_id_seq
-- ----------------------------
drop sequence if exists "public"."${table.sqlName}_id_seq";
create sequence "public"."${table.sqlName}_id_seq"
  increment 1
  minvalue  1
  maxvalue 9223372036854775807
  start 1
  cache 1;
