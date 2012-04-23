SET client_encoding = '${charset}';
--drop table cap_comuni;
--drop table comuni;
create table comuni (
	codice_istat varchar(6) not null primary key,
	codice_catastale varchar(4) not null,
	nome varchar(60) not null,
	provincia varchar(2) not null
);

create table cap_comuni (
	cap varchar(6)  not null,
	codice_istat_comune varchar(6) not null,
	primary key(cap,codice_istat_comune)
);

ALTER TABLE cap_comuni ADD FOREIGN KEY (codice_istat_comune) REFERENCES comuni (codice_istat) ON DELETE CASCADE;
begin;
<#list comuni as comune>
insert into comuni(codice_istat,codice_catastale, nome, provincia) values('${comune.codiceIstat?replace("'", "''")}','${comune.codiceCatastale?replace("'", "''")}','${comune.nome?replace("'", "''")}','${comune.provincia?replace("'", "''")}');
</#list>
<#list comuni as comune>
    <#list comune.codiciCap as codiceCap>
insert into cap_comuni(cap, codice_istat_comune) values('${codiceCap?replace("'", "''")}','${comune.codiceIstat?replace("'", "''")}');
	</#list>
</#list>
commit;