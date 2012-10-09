class User < ActiveRecord::Base
  has_many :Card
  attr_accessible :name, :password, :username
end
