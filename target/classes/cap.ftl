SET client_encoding = '${charset}';
--drop table cap;
create table cap (
	id serial primary key,
	cap varchar(6)  not null,
	nome_comune varchar(60) not null,
	provincia varchar(2) not null
);

begin;
<#list comuni as comune>
    <#list comune.capAssociati as cap>
insert into cap(cap, nome_comune, provincia) values('${cap.cap?replace("'", "''")}','${cap.comune?replace("'", "''")}','${cap.provincia?replace("'", "''")}');
	</#list>
</#list>
commit;