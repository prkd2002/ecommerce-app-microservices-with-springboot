-- Kategorien einfügen
INSERT INTO public.category (id, name, description) VALUES
                                                 (1, 'Elektronik', 'Geräte und Zubehör'),
                                                 (2, 'Haushalt', 'Produkte für den täglichen Gebrauch'),
                                                 (3, 'Sport', 'Sportausrüstung und Kleidung');

-- Produkte einfügen
INSERT INTO public.product (id, name, description, available_quantity, price, category_id) VALUES
                                                                                        (1, 'Smartphone', 'Android Smartphone mit 6.5 Zoll Display', 120, 399.99, 1),
                                                                                        (2, 'Bluetooth Kopfhörer', 'Kabellose Kopfhörer mit Geräuschunterdrückung', 75, 89.90, 1),
                                                                                        (3, 'Wasserkocher', '1.7L Edelstahl Wasserkocher', 50, 29.99, 2),
                                                                                        (4, 'Tennisball-Set', '4er Set Tennisbälle', 200, 12.50, 3),
                                                                                        (5, 'Yogamatte', 'Rutschfeste Yogamatte 180x60 cm', 80, 24.95, 3);
