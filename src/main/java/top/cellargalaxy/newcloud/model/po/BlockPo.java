package top.cellargalaxy.newcloud.model.po;

import java.io.Serializable;

/**
 * @author cellargalaxy
 * @time 2018/7/3
 */
public class BlockPo implements Serializable {
	private static final long serialVersionUID = 4217431251828547844L;
	private int blockId;
	private byte[] block;

	public int getBlockId() {
		return blockId;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}

	public byte[] getBlock() {
		return block;
	}

	public void setBlock(byte[] block) {
		this.block = block;
	}

	@Override
	public String toString() {
		return "BlockPo{" +
				"blockId=" + blockId +
				'}';
	}
}
