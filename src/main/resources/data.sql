DO
$$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'event') THEN
            CREATE TABLE event
            (
                eventID            SERIAL PRIMARY KEY,
                name               VARCHAR(255) NOT NULL,
                event_type         VARCHAR(255) NOT NULL,
                discount_value     INT          NOT NULL,
                start_at           DATE         NOT NULL,
                end_at             DATE         NOT NULL,
                max_issued_coupons INT          NOT NULL
            );
            INSERT INTO event (name, event_type, discount_value, start_at, end_at, max_issued_coupons)
            VALUES
                ('Event 1', 'FIXED', 10, '2024-11-01', '2024-12-02', 50),
                ('Event 2', 'PERCENTAGE', 20, '2024-11-03', '2024-12-04', 100),
                ('Event 3', 'FIXED', 30, '2024-11-05', '2024-12-06', 150);
        END IF;
    END
$$;;