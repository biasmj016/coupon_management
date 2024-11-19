DO
$$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'event') THEN
            CREATE TABLE event
            (
                eventID            SERIAL PRIMARY KEY,
                name               VARCHAR(255) NOT NULL,
                start_at           DATE         NOT NULL,
                end_at             DATE         NOT NULL,
                max_issued_coupons INT          NOT NULL
            );
            INSERT INTO event (name, start_at, end_at, max_issued_coupons)
            VALUES
                   ('Event 1', '2024-11-01', '2024-12-02', 50),
                   ('Event 2', '2024-11-03', '2024-12-04', 100),
                   ('Event 3', '2024-11-05', '2024-12-06', 150);
        END IF;
    END
$$;;