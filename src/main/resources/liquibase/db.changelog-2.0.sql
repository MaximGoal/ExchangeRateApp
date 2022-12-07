--liquibase formatted sql

--changeset golov:1
insert into currency_pair(id, base_charcode, quoted_charcode) values
	(1, 'AUD', 'AZN'),
	(2, 'AZN', 'AUD'),
	(3, 'USD', 'EUR'),
	(4, 'EUR', 'USD'),
	(5, 'RUR', 'USD'),
	(6, 'USD', 'RUR');

--changeset golov:2
insert into exchange_rate (id, rate_date, rate_value, currency_pair_id) values
    (1, TO_TIMESTAMP('2022-12-06 23:30:25.505355','YYYY-MM-DD HH24:MI:SS.FF'), 0.9490907192230225, 3),
    (2, TO_TIMESTAMP('2022-12-06 23:30:25.512150','YYYY-MM-DD HH24:MI:SS.FF'), 1.0536400079727173, 4),
    (3, TO_TIMESTAMP('2022-12-06 23:30:25.518257','YYYY-MM-DD HH24:MI:SS.FF'), 1.1629687547683716, 1),
    (4, TO_TIMESTAMP('2022-12-06 23:30:25.524800','YYYY-MM-DD HH24:MI:SS.FF'), 0.0160810742527246, 6),
    (5, TO_TIMESTAMP('2022-12-06 23:30:25.531019','YYYY-MM-DD HH24:MI:SS.FF'), 0.8598682880401611, 2),
    (6, TO_TIMESTAMP('2022-12-06 23:30:25.538939','YYYY-MM-DD HH24:MI:SS.FF'), 62.184898376464844, 5);

