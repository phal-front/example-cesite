INSERT INTO kind(kind_id, kind_name)VALUES('100', '本');
INSERT INTO kind(kind_id, kind_name)VALUES('200', 'CD');



INSERT INTO item(item_id, item_name, item_price, item_stock)VALUES('100001', 'Cの本', 1200, 20);
INSERT INTO item(item_id, item_name, item_price, item_stock)VALUES('100002', 'C++の本', 1500, 10);
INSERT INTO item(item_id, item_name, item_price, item_stock)VALUES('100003', 'Objectiv-Cの本', 1500, 10);
INSERT INTO item(item_id, item_name, item_price, item_stock)VALUES('100011', 'Javaの本', 1200, 20);
INSERT INTO item(item_id, item_name, item_price, item_stock)VALUES('100012', 'Scalaの本', 1500, 10);
INSERT INTO item(item_id, item_name, item_price, item_stock)VALUES('100013', 'Groovyの本', 1500, 10);
INSERT INTO item(item_id, item_name, item_price, item_stock)VALUES('100031', 'JavaScriptの本', 1000, 30);
INSERT INTO item(item_id, item_name, item_price, item_stock)VALUES('100032', 'TypeScriptの本', 1800, 10);
INSERT INTO item(item_id, item_name, item_price, item_stock)VALUES('100101', 'TCP/IPの本', 6000, 5);
INSERT INTO item(item_id, item_name, item_price, item_stock)VALUES('200001', '邦楽A', 1200, 20);
INSERT INTO item(item_id, item_name, item_price, item_stock)VALUES('200002', '邦楽B', 1200, 20);
INSERT INTO item(item_id, item_name, item_price, item_stock)VALUES('200003', '邦楽C', 1200, 20);
INSERT INTO item(item_id, item_name, item_price, item_stock)VALUES('200101', '洋楽A', 1500, 5);
INSERT INTO item(item_id, item_name, item_price, item_stock)VALUES('200102', '洋楽B', 1500, 5);
INSERT INTO item(item_id, item_name, item_price, item_stock)VALUES('200103', '洋楽C', 1500, 5);



INSERT INTO kind_item(kind_id, item_id)VALUES('100', '100001');
INSERT INTO kind_item(kind_id, item_id)VALUES('100', '100002');
INSERT INTO kind_item(kind_id, item_id)VALUES('100', '100003');
INSERT INTO kind_item(kind_id, item_id)VALUES('100', '100011');
INSERT INTO kind_item(kind_id, item_id)VALUES('100', '100012');
INSERT INTO kind_item(kind_id, item_id)VALUES('100', '100013');
INSERT INTO kind_item(kind_id, item_id)VALUES('100', '100031');
INSERT INTO kind_item(kind_id, item_id)VALUES('100', '100032');
INSERT INTO kind_item(kind_id, item_id)VALUES('100', '100101');
INSERT INTO kind_item(kind_id, item_id)VALUES('200', '200001');
INSERT INTO kind_item(kind_id, item_id)VALUES('200', '200002');
INSERT INTO kind_item(kind_id, item_id)VALUES('200', '200003');
INSERT INTO kind_item(kind_id, item_id)VALUES('200', '200101');
INSERT INTO kind_item(kind_id, item_id)VALUES('200', '200102');
INSERT INTO kind_item(kind_id, item_id)VALUES('200', '200103');



