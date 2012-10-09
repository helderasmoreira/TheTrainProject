class Card < ActiveRecord::Base
  belongs_to :User
  attr_accessible :number, :type, :validity
end
