CREATE DATABASE IF NOT EXISTS bridge_deals_database;
USE bridge_deals_database;

-- Game Inserts (already in the desired format relative to the USE statement)
INSERT INTO `Game` (`id`, `contract`) VALUES ('1', '3NT');
INSERT INTO `Game` (`id`, `contract`) VALUES ('2', '6S');
INSERT INTO `Game` (`id`, `contract`) VALUES ('3', '3D');
INSERT INTO `Game` (`id`, `contract`) VALUES ('4', '3NT');

-- `Suit` Inserts (transformed format)
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('1', 'J32', 'N', 'S', '1');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('2', 'A32', 'N', 'H', '1');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('3', 'K32', 'N', 'D', '1');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('4', 'AQ32', 'N', 'C', '1');

INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('5', 'KT987', 'E', 'S', '1');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('6', '9876', 'E', 'H', '1');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('7', 'T', 'E', 'C', '1');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('8', 'QJT', 'E', 'D', '1');

INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('9', 'Q4', 'S', 'S', '1');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('10', 'K54', 'S', 'H', '1');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('11', 'KJ54', 'S', 'C', '1');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('12', 'A654', 'S', 'D', '1');

INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('13', 'A65', 'W', 'S', '1');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('14', 'QJT', 'W', 'H', '1');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('15', '9876', 'W', 'C', '1');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('16', '987', 'W', 'D', '1');

INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('17', 'AK32', 'N', 'S', '2');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('18', '32', 'N', 'H', '2');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('19', 'AQJT', 'N', 'C', '2');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('20', 'QT9', 'N', 'D', '2');

INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('21', '7', 'E', 'S', '2');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('22', 'JT98', 'E', 'H', '2');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('23', '654', 'E', 'C', '2');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('24', '87654', 'E', 'D', '2');

INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('25', 'JT98', 'S', 'S', '2');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('26', 'KQ', 'S', 'H', '2');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('27', 'K987', 'S', 'C', '2');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('28', 'AKJ', 'S', 'D', '2');

INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('29', 'Q654', 'W', 'S', '2');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('30', 'A7654', 'W', 'H', '2');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('31', '32', 'W', 'C', '2');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('32', '32', 'W', 'D', '2');

INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('33', 'T84', 'S', 'S', '3');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('34', 'KD6', 'S', 'H', '3');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('35', 'A8', 'S', 'D', '3');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('36', 'KJ842', 'S', 'C', '3');

INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('37', 'AKD93', 'E', 'S', '3');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('38', '82', 'E', 'H', '3');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('39', '92', 'E', 'D', '3');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('40', 'AT65', 'E', 'C', '3');

INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('41', '72', 'N', 'S', '3');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('42', 'AJT4', 'N', 'H', '3');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('43', 'QJT43', 'N', 'D', '3');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('44', 'D3', 'N', 'C', '3');

INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('45', 'J65', 'W', 'S', '3');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('46', '9753', 'W', 'H', '3');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('47', 'K765', 'W', 'D', '3');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('48', '97', 'W', 'C', '3');

INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('49', 'AT2', 'N', 'S', '4');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('50', 'K54', 'N', 'H', '4');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('51', '987', 'N', 'D', '4');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('52', 'AK76', 'N', 'C', '4');

INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('53', 'K9876', 'E', 'S', '4');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('54', '876', 'E', 'H', '4');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('55', '32', 'E', 'D', '4');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('56', 'QJT', 'E', 'C', '4');

INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('57', 'J3', 'S', 'S', '4');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('58', 'A32', 'S', 'H', '4');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('59', 'KQJT', 'S', 'D', '4');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('60', '5432', 'S', 'C', '4');

INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('61', 'Q54', 'W', 'S', '4');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('62', 'QJT9', 'W', 'H', '4');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('63', 'A654', 'W', 'D', '4');
INSERT INTO `Suit` (`id`, `card_values`, `seat`, `suit_name`, `game_id`) VALUES ('64', '98', 'W', 'C', '4');