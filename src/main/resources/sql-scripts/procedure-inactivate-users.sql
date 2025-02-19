DROP PROCEDURE inactivate_users;

DELIMITER $$

CREATE PROCEDURE inactivate_users ()
BEGIN
    update users set status = 0, date_updated=now(), updated_by=7 where datediff(now(), last_login_at)/30 >= 6;
END;
$$

DELIMITER ;