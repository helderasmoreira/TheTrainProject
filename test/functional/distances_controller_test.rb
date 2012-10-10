require 'test_helper'

class DistancesControllerTest < ActionController::TestCase
  setup do
    @distance = distances(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:distances)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create distance" do
    assert_difference('Distance.count') do
      post :create, distance: { distance: @distance.distance, stop1_id: @distance.stop1_id, stop2_id: @distance.stop2_id }
    end

    assert_redirected_to distance_path(assigns(:distance))
  end

  test "should show distance" do
    get :show, id: @distance
    assert_response :success
  end

  test "should get edit" do
    get :edit, id: @distance
    assert_response :success
  end

  test "should update distance" do
    put :update, id: @distance, distance: { distance: @distance.distance, stop1_id: @distance.stop1_id, stop2_id: @distance.stop2_id }
    assert_redirected_to distance_path(assigns(:distance))
  end

  test "should destroy distance" do
    assert_difference('Distance.count', -1) do
      delete :destroy, id: @distance
    end

    assert_redirected_to distances_path
  end
end
