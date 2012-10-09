class Card < ActiveRecord::Base
  belongs_to :user
  attr_accessible :number, :type, :validity
end
