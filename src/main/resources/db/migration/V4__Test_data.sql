-- start test data for developing
INSERT INTO
    admin_db.part_and_service (description, type)
VALUES
    ('Автобензин АИ-95', 1),
    ('Мойка машины', 2),
    (
        'Тормозные колодки передние комплект TRW GDB2108',
        3
    );

INSERT INTO
    admin_db.spending (
        date,
        part_and_service_id,
        description,
        count,
        amount
    )
VALUES
    ('2024-06-16', 1, NULL, 30.0, 1615.15),
    ('2024-06-20', 2, 'кузов + коврики', 1.0, 100.15),
    ('2024-06-25', 3, NULL, 1.0, 2400.0);