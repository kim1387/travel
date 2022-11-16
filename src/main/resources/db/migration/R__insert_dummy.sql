# insert into city (id, created_date, is_activated, updated_date, intro_content, name, view, latest_view_at)
# values (1, now(), true, now(), '여기는 서울', '서울', 0, now()),
#        (2, now(), true, now(), '여기는 수원', '수원', 0, now()),
#        (3, now(), true, now(), '여기는 부산', '부산', 0, now()),
#        (4, now(), true, now(), '여기는 강릉', '강릉', 0, now()),
#        (5, now(), true, now(), '여기는 일산', '일산', 0, now());
#
# insert into users (id, created_date, is_activated, updated_date, name, role)
# values (1, now(), true, now(), '김기현', 'ROLE_USER'),
#        (2, now(), true, now(), '김영한', 'ROLE_USER'),
#        (3, now(), true, now(), '백기선', 'ROLE_USER'),
#        (4, now(), true, now(), '최범균', 'ROLE_USER'),
#        (5, now(), true, now(), '토비', 'ROLE_USER');
#
#
# insert into travel (id, created_date, is_activated, updated_date, end_at, start_at, city_id, users_id)
# values (1, now(), true, now(), DATE_ADD(now()+1), DATE_ADD(now()-1), 1, 1),
#        (2, now()-1, true, now(), DATE_ADD(now()+2), DATE_ADD(now()-1), 2, 1),
#        (3, now()-1, true, now(), DATE_ADD(now()+2), DATE_ADD(now()), 3, 1),
#        (4, now()-2, true, now(), DATE_ADD(now()+3), DATE_ADD(now()+1), 4, 1),
#        (5, now()-3, true, now(), DATE_ADD(now()+4), DATE_ADD(now()+2), 5, 1),
#        (6, now()-1, true, now(), DATE_ADD(now()+5), DATE_ADD(now()+3), 1, 1),
#        (7, now(), true, now(), DATE_ADD(now()+6), DATE_ADD(now()+4), 2, 1),
#        (8, now()-3, true, now(), DATE_ADD(now()+7), DATE_ADD(now()+5), 3, 1),
#        (9, now()-1, true, now(), DATE_ADD(now()+8), DATE_ADD(now()+1), 4, 1),
#        (10, now()-1, true, now(), DATE_ADD(now()+9), DATE_ADD(now()+1), 5, 1),
#        (11, now(), true, now(), DATE_ADD(now()+10), DATE_ADD(now()+1), 1, 1),
#        (11, now(), true, now(), DATE_ADD(now()+11), DATE_ADD(now()+1), 2, 2);
