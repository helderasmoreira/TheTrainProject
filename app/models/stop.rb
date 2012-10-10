class Stop < ActiveRecord::Base
	has_many :stop1_id, :class_name => "Distance", :foreign_key => "stop1_id"
	has_many :stop2_id, :class_name => "Distance", :foreign_key => "stop2_id"
	attr_accessible :location
end
