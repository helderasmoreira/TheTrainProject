class Distance < ActiveRecord::Base
	belongs_to :stop1_id, :class_name => "Stop"
	belongs_to :stop2_id, :class_name => "Stop"
 	attr_accessible :distance, :stop1_id, :stop2_id
end
