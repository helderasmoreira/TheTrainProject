class User < ActiveRecord::Base
  has_many :cards
  attr_accessible :name, :password, :username
end
