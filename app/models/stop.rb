class Stop < ActiveRecord::Base
	has_many :stops1, :class_name => "Distance", :foreign_key => "stop1_id"
	has_many :stops2, :class_name => "Distance", :foreign_key => "stop2_id"
	attr_accessible :location
end
