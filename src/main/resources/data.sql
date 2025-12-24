TRUNCATE TABLE
    notifications,
    product_history,
    products,
    categories,
    zones,
    fridge_memberships,
    fridges,
    user_roles,
    users,
    roles
    RESTART IDENTITY CASCADE@@

INSERT INTO roles (code, name) VALUES
                                   ('ADMIN','Administrator'),
                                   ('MODERATOR','Moderator'),
                                   ('USER','User')@@

-- bcrypt("password")
INSERT INTO users (email,password_hash,name,room,notif_email_on,notif_push_on)
VALUES
    ('alice@example.com','$2a$10$9pQvG7yYlXl7u6Qy1cG3uO8t7o9xC0P9oGzQqE9b6GmY7G3tqfQK2','Alice','101', TRUE, TRUE),
    ('bob@example.com','$2a$10$9pQvG7yYlXl7u6Qy1cG3uO8t7o9xC0P9oGzQqE9b6GmY7G3tqfQK2','Bob','102', TRUE, FALSE)@@

INSERT INTO user_roles (user_id, role_id) VALUES
                                              (1, 1),
                                              (2, 3)@@

INSERT INTO fridges (name, location, invite_required)
VALUES ('Kitchen Fridge', 'Apartment 1, Kitchen', FALSE)@@

SELECT fn_add_fridge_member(1, 1, TRUE)@@
SELECT fn_add_fridge_member(1, 2, FALSE)@@

       INSERT INTO zones (fridge_id, name, capacity_units, capacity_volume_l, is_active, sort_order)
VALUES
    (1, 'Top Shelf', 20, 50.0, TRUE, 1),
    (1, 'Bottom Drawer', 10, 30.0, TRUE, 2)@@

INSERT INTO categories (name, perishable_days_default)
VALUES
    ('Dairy', 7),
    ('Veggies', 10),
    ('Canned', NULL)@@

SELECT fn_add_product(1, 1, 1, 'Strawberry Yogurt 150g', '0123456789012', NULL, NULL, FALSE, 'ACTIVE')@@
SELECT fn_add_product(2, 2, 2, 'Carrots (1 kg)', NULL, now() + interval '8 days', NULL, FALSE, 'ACTIVE')@@
SELECT fn_add_product(2, 1, 3, 'Canned Beans 400g', NULL, NULL, NULL, FALSE, 'ACTIVE')@@
