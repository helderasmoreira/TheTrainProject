class User < ActiveRecord::Base
  has_many :cards
  has_many :tickets, :class_name => "Ticket", :foreign_key => "user_id"
  attr_accessible :name, :password, :username, :cards, :tickets
end
