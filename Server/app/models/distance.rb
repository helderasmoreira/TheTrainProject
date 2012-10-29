class Distance < ActiveRecord::Base
	belongs_to :stop1, :class_name => "Stop"
	belongs_to :stop2, :class_name => "Stop"
	validates_uniqueness_of :stop1_id, :scope => :stop2_id
 	attr_accessible :distance, :stop1_id, :stop2_id
end
