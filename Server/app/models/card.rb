class Card < ActiveRecord::Base
  belongs_to :user
  attr_accessible :number, :card_type, :validity, :user_id, :cvv
end
