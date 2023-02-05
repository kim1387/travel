insert into test_db.users (id, created_date, is_activated, updated_date, name, role)
values (1, now(), true, now(), '김기현', 'ROLE_USER'),
       (2, now(), true, now(), '김영한', 'ROLE_USER'),
       (3, now(), true, now(), '백기선', 'ROLE_USER'),
       (4, now(), true, now(), '최범균', 'ROLE_USER'),
       (5, now(), true, now(), '토비', 'ROLE_USER');

insert into city (id, created_date, is_activated, updated_date, intro_content, latest_view_at, name, view, users_id)
values (1, now(), true, now(), '여기는 서울', now(), '서울', 0, 1),
       (2, now(), true, now(), '여기는 수원', now(),'수원', 0, 2),
       (3, now(), true, now(), '여기는 부산', now(),'부산', 0, 1),
       (4, now(), true, now(), '여기는 강릉', now(),'강릉', 0, 1),
       (5, now(), true, now(), '여기는 일산',now(), '일산', 0, 1),
       (6, now(), true, now(), '여기는 대구',now(), '대구', 0, 1),
       (7, now(), true, now(), '여기는 울산',now(), '울산', 0, 1),
       (8, now(), true, now(), '여기는 포항',now(), '포항', 0, 1),
       (9, now(), true, now(), '여기는 시흥',now(), '시흥', 0, 1),
       (10, now(), true, now(), '여기는 강북',now(), '강북', 0, 1),
       (11, now(), true, now(), '여기는 강남',now(), '강남', 0, 1),
       (12, now(), true, now(), '여기는 판교',now(), '판교', 0, 1),
       (13, now(), true, now(), '여기는 제주',now(), '제주', 0, 1);
